package com.projeto.pos.biblioteca.spring.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.projeto.pos.biblioteca.spring.dto.ExemplarDTO;

public interface ExemplarService {
    ExemplarDTO create(ExemplarDTO dto);
    ExemplarDTO update(UUID id, ExemplarDTO dto);
    void delete(UUID id);
    ExemplarDTO findById(UUID id);
    List<ExemplarDTO> findByLivroId(UUID livroId);
    List<ExemplarDTO> findAll();   // ✅ corrigido
   // Object countByStatus(UUID livroId);
    Map<String, Long> countByStatus(UUID livroId);
}
