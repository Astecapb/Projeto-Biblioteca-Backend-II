package com.projeto.pos.biblioteca.spring.model;

/**
 * Status do exemplar: disponível, emprestado ou reservado.
 * Usamos Enum para facilitar filtros e contagens.
 */
public enum ExemplarStatus {
    DISPONIVEL,
    EMPRESTADO,
    RESERVADO
}