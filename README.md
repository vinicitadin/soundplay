# SoundPlay

API REST que implementa o backend de um serviço de streaming de música (no estilo do Spotify). Permite cadastrar artistas, álbuns e músicas, criar playlists pessoais e controlar o acesso por meio de autenticação JWT.

## Funcionalidades

- **Autenticação e autorização** com JWT (login e registro de usuários)
- **Artistas**: cadastro, listagem, busca por nome, atualização e exclusão
- **Álbuns**: cadastro vinculado a um artista, busca por título, atualização e exclusão
- **Músicas**: cadastro com artista e álbum opcionais, busca por título, atualização e exclusão
- **Playlists**: criação, edição e exclusão, listagem das playlists do usuário logado, adição e remoção de músicas (somente o dono da playlist pode alterá-la)
- **Documentação interativa** com Swagger UI (OpenAPI)
- **Migrations versionadas** do banco de dados com Flyway

## Tecnologias

| Categoria | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4.1.1 (Web MVC, Data JPA, Validation, Security) |
| Banco de dados | PostgreSQL |
| Migrations | Flyway |
| Autenticação | JWT (`com.auth0:java-jwt`) + BCrypt |
| Documentação | springdoc-openapi (Swagger UI) |
| Build | Maven (Maven Wrapper incluso) |
| Outros | Lombok |

## Pré-requisitos

- [JDK 21](https://adoptium.net/) ou superior
- [PostgreSQL](https://www.postgresql.org/download/) em execução (porta `5432`)
- Não é necessário instalar o Maven: o projeto inclui o wrapper (`mvnw` / `mvnw.cmd`)

## Como executar

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd soundplay
```

### 2. Crie o banco de dados

```sql
CREATE DATABASE soundplay;
```

### 3. Configure a conexão

Ajuste o arquivo `src/main/resources/application.properties` com as credenciais do seu PostgreSQL:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/soundplay
spring.datasource.username=<seu-usuario>
spring.datasource.password=<sua-senha>
```

As tabelas são criadas automaticamente pelo Flyway na primeira execução (`spring.jpa.hibernate.ddl-auto=validate` apenas confere se o schema bate com as entidades).

### 4. Inicie a aplicação

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

**Windows**

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

### Gerar o JAR

```bash
./mvnw clean package
java -jar target/SoundPlay-0.0.1-SNAPSHOT.jar
```

### Executar os testes

```bash
./mvnw test
```

## Documentação da API (Swagger)

Com a aplicação rodando, acesse:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI (JSON):** http://localhost:8080/v3/api-docs

Para testar os endpoints protegidos pelo Swagger, faça login em `POST /auth/login`, copie o token retornado e clique em **Authorize**, informando o token (sem o prefixo `Bearer`).

## Autenticação

Todos os endpoints exigem autenticação, exceto `/auth/login`, `/auth/register` e as rotas do Swagger.

**1. Registrar um usuário**

```http
POST /auth/register
Content-Type: application/json

{
  "nome": "Maria Silva",
  "email": "maria@email.com",
  "senha": "minhasenha123"
}
```

**2. Fazer login**

```http
POST /auth/login
Content-Type: application/json

{
  "email": "maria@email.com",
  "senha": "minhasenha123"
}
```

Resposta:

```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

**3. Usar o token nas demais requisições**

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

O token é válido por 24 horas.

## Endpoints

### Autenticação — `/auth`

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/auth/register` | Registra um novo usuário | Público |
| POST | `/auth/login` | Autentica e retorna o token JWT | Público |

### Artistas — `/artistas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/artistas` | Lista todos os artistas |
| GET | `/artistas/buscar?nome=` | Pesquisa artistas por nome |
| POST | `/artistas` | Cadastra um artista |
| PUT | `/artistas/{id}` | Atualiza um artista |
| DELETE | `/artistas/{id}` | Exclui um artista |

### Álbuns — `/albuns`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/albuns` | Lista todos os álbuns |
| GET | `/albuns/buscar?titulo=` | Pesquisa álbuns por título |
| POST | `/albuns` | Cadastra um álbum (exige `artistaId`) |
| PUT | `/albuns/{id}` | Atualiza um álbum |
| DELETE | `/albuns/{id}` | Exclui um álbum |

### Músicas — `/musicas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/musicas` | Lista todas as músicas |
| GET | `/musicas/{id}` | Busca uma música por ID |
| GET | `/musicas/buscar?titulo=` | Pesquisa músicas por título (ignora maiúsculas/minúsculas) |
| POST | `/musicas` | Cadastra uma música |
| PUT | `/musicas/{id}` | Atualiza uma música |
| DELETE | `/musicas/{id}` | Exclui uma música |

### Playlists — `/playlists`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/playlists` | Lista todas as playlists |
| GET | `/playlists/minhas` | Lista as playlists do usuário autenticado |
| GET | `/playlists/{id}` | Busca uma playlist por ID |
| POST | `/playlists` | Cria uma playlist (o dono é o usuário autenticado) |
| PUT | `/playlists/{id}` | Atualiza uma playlist |
| DELETE | `/playlists/{id}` | Exclui uma playlist |
| POST | `/playlists/{id}/musicas/{musicaId}` | Adiciona uma música à playlist (somente o dono) |
| DELETE | `/playlists/{id}/musicas/{musicaId}` | Remove uma música da playlist (somente o dono) |

Respostas de erro mais comuns: `400` (dados inválidos), `403` (playlist de outro usuário), `404` (recurso não encontrado) e `409` (música já presente na playlist).

### Usuários — `/usuarios`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/usuarios` | Lista os usuários |
| GET | `/usuarios/{id}` | Busca um usuário por ID |
| POST | `/usuarios` | Cadastra um usuário |
| PUT | `/usuarios/{id}` | Atualiza um usuário |
| DELETE | `/usuarios/{id}` | Exclui um usuário |

## Modelo de dados

```
usuario ──< playlist >──< playlist_musica >── musica
                                                │  │
                                  artista ──────┘  │
                                     │             │
                                     └──< album >──┘
```

- Um **usuário** possui várias **playlists**
- Uma **playlist** contém várias **músicas** (relação N:N pela tabela `playlist_musica`)
- Um **artista** possui vários **álbuns**
- Uma **música** pode pertencer a um artista e a um álbum (ambos opcionais)
- Ao excluir um usuário, suas playlists são removidas; ao excluir um artista ou álbum, as músicas ficam sem vínculo (`ON DELETE SET NULL`)

## Estrutura do projeto

```
src/main/java/com/example/soundplay
├── config/        # Segurança, JWT, filtro de autenticação e OpenAPI
├── controller/    # Endpoints REST
├── doc/           # Interfaces com as anotações do Swagger (uma por controller)
├── dto/
│   ├── request/   # Objetos de entrada com validações
│   └── response/  # Objetos de saída
├── entity/        # Entidades JPA
├── repository/    # Repositórios Spring Data
└── service/       # Regras de negócio

src/main/resources
├── application.properties
└── db/migration/  # Migrations do Flyway (V1 a V6)
```

As anotações do Swagger ficam separadas em interfaces na pasta `doc/`, e cada controller as implementa. Isso mantém os controllers limpos e a documentação centralizada.
