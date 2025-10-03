-- Criação da tabela autor
CREATE TABLE autor (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL
);

-- Criação da tabela livro
CREATE TABLE livro (
  id SERIAL PRIMARY KEY,
  titulo VARCHAR(500) NOT NULL,
  isbn VARCHAR(50),
  editora VARCHAR(255),
  ano INTEGER
);

-- Tabela de associação livro_autor (ManyToMany)
CREATE TABLE livro_autor (
  livro_id INTEGER NOT NULL REFERENCES livro(id) ON DELETE CASCADE,
  autor_id INTEGER NOT NULL REFERENCES autor(id) ON DELETE CASCADE,
  PRIMARY KEY (livro_id, autor_id)
);

-- Enum status
CREATE TYPE exemplar_status AS ENUM ('DISPONIVEL', 'EMPRESTADO', 'RESERVADO');

-- Tabela exemplar_livro
CREATE TABLE exemplar_livro (
  id SERIAL PRIMARY KEY,
  codigo VARCHAR(100) UNIQUE NOT NULL,
  status exemplar_status NOT NULL DEFAULT 'DISPONIVEL',
  livro_id INTEGER NOT NULL REFERENCES livro(id) ON DELETE CASCADE
);
--ADICIONADO DE V4_parte2_completa.sql
-- Cria tabela usuário
CREATE TABLE usuario_biblioteca (
    id UUID PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL
);

-- Cria tabela empréstimo
CREATE TABLE emprestimo (
    id UUID PRIMARY KEY,
    data_hora_emprestimo TIMESTAMP NOT NULL,
    data_hora_devolucao TIMESTAMP,
    status VARCHAR(20) NOT NULL,
    usuario_id UUID NOT NULL REFERENCES usuario_biblioteca(id),
    exemplar_id UUID NOT NULL REFERENCES exemplar_livro(id)
);

-- Índices para performance
CREATE INDEX idx_emprestimo_usuario ON emprestimo(usuario_id);
CREATE INDEX idx_emprestimo_exemplar ON emprestimo(exemplar_id);
CREATE INDEX idx_emprestimo_data ON emprestimo(data_hora_emprestimo);
