-- Script SQL para criar banco de dados 'literatura'
-- Executar como superuser (postgres)

-- Criar banco de dados
CREATE DATABASE literatura
    ENCODING 'UTF8'
    LC_COLLATE 'C'
    LC_CTYPE 'C'
    TEMPLATE template0;

-- Conectar ao banco
\c literatura

-- Criar schema (opcional, padrão é 'public')
-- O Hibernate criará as tabelas automaticamente com ddl-auto=update

GRANT ALL PRIVILEGES ON DATABASE literatura TO postgres;

-- Confirmar
\l

