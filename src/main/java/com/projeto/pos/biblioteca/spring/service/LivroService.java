package com.projeto.pos.biblioteca.spring.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.pos.biblioteca.spring.dto.LivroDTO;

public interface LivroService {
    LivroDTO create(LivroDTO dto);
    LivroDTO update(UUID id, LivroDTO dto);
    void delete(UUID id);
    LivroDTO findById(UUID id);
    Page<LivroDTO> findAll(String tituloFilter, Pageable pageable);
    long countExemplares(UUID livroId);
    long countExemplaresByStatus(UUID livroId, String status);
    // opcional: retornar um objeto com contagens por status
    Object countByAllStatuses(UUID livroId);
}

