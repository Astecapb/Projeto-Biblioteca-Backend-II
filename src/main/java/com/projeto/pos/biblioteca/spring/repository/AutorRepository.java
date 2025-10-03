package com.projeto.pos.biblioteca.spring.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.pos.biblioteca.spring.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    // Buscar autor por nome (ignora maiúsculas/minúsculas)
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}
