CREATE TABLE album(
    id BIGSERIAL primary key,
    titulo VARCHAR(200) NOT NULL,
    ano_lancamento INTEGER,
    capa VARCHAR(500)
);