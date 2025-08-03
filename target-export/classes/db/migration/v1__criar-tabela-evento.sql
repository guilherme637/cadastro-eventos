CREATE TABLE eventos (
     id SERIAL PRIMARY KEY,
     titulo VARCHAR(100) NOT NULL,
     descricao VARCHAR(1000),
     data_evento TIMESTAMP NOT NULL,
     local VARCHAR(200),
     deletado BOOLEAN NOT NULL DEFAULT false
    );
