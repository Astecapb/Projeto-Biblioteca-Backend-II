package com.projeto.pos.biblioteca.spring.exception;


/**
 * Exceção para recurso não encontrado.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super(String.format("%s não encontrado com id '%s'", resource, id));
    }
}
