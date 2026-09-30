CREATE TABLE artista(
    id BIGSERIAL primary key,
    nome VARCHAR(150) NOT NULL,
    genero VARCHAR(50),
    biografia TEXT
);