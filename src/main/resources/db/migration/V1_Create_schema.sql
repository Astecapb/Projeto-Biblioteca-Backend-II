-- ======================================================
--  SCRIPT COMPLETO – UUID EM TODAS AS CHAVES PRIMÁRIAS
-- ======================================================

-- 1. TABELA AUTOR
CREATE TABLE autor (
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome  VARCHAR(255) NOT NULL,
    cpf   VARCHAR(14)  NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- 2. TABELA LIVRO
CREATE TABLE livro (
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo  VARCHAR(500) NOT NULL,
    isbn    VARCHAR(50),
    editora VARCHAR(255),
    ano     INTEGER
);

-- 3. ASSOCIAÇÃO MANY-TO-MANY
CREATE TABLE livro_autor (
    livro_id UUID NOT NULL REFERENCES livro(id) ON DELETE CASCADE,
    autor_id UUID NOT NULL REFERENCES autor(id) ON DELETE CASCADE,
    PRIMARY KEY (livro_id, autor_id)
);

-- 4. ENUM STATUS EXEMPLAR
CREATE TYPE exemplar_status AS ENUM ('DISPONIVEL', 'EMPRESTADO', 'RESERVADO');

-- 5. TABELA EXEMPLAR_LIVRO
CREATE TABLE exemplar_livro (
    id     UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    codigo VARCHAR(100) UNIQUE NOT NULL,
    status exemplar_status NOT NULL DEFAULT 'DISPONIVEL',
    livro_id UUID NOT NULL REFERENCES livro(id) ON DELETE CASCADE
);

-- 6. TABELA USUÁRIO
CREATE TABLE usuario_biblioteca (
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome  VARCHAR(120) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    cpf   VARCHAR(14)  UNIQUE NOT NULL
);

-- 7. TABELA EMPRÉSTIMO
CREATE TABLE emprestimo (
    id                    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    data_hora_emprestimo  TIMESTAMP NOT NULL,
    data_hora_devolucao   TIMESTAMP,
    status                VARCHAR(20) NOT NULL,
    usuario_id            UUID NOT NULL REFERENCES usuario_biblioteca(id),
    exemplar_id           UUID NOT NULL REFERENCES exemplar_livro(id)
);

-- 8. ÍNDICES DE PERFORMANCE
CREATE INDEX idx_emprestimo_usuario  ON emprestimo(usuario_id);
CREATE INDEX idx_emprestimo_exemplar ON emprestimo(exemplar_id);
CREATE INDEX idx_emprestimo_data     ON emprestimo(data_hora_emprestimo);


ALTER TABLE livro
ADD COLUMN status_conservacao VARCHAR(20) NOT NULL DEFAULT 'NOVO';