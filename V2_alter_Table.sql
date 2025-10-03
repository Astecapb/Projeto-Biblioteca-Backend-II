-- Cria o enum (opcional, mas limpo)
CREATE TYPE status_conservacao_enum AS ENUM ('NOVO', 'BOM', 'REGULAR');

-- Adiciona coluna à tabela exemplar_livro
ALTER TABLE exemplar_livro
    ADD COLUMN status_conservacao status_conservacao_enum;

-- Índice para buscas rápidas
CREATE INDEX idx_exemplar_conservacao ON exemplar_livro(status_conservacao);