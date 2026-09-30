CREATE TABLE musica (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    duracao INT NOT NULL,
    url VARCHAR(500)
);