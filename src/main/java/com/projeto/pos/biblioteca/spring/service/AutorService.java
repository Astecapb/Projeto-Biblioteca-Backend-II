package com.projeto.pos.biblioteca.spring.service;

import java.util.List;
import java.util.UUID;

import com.projeto.pos.biblioteca.spring.dto.AutorDTO;




public interface AutorService {
    AutorDTO create(AutorDTO dto);
    AutorDTO update(UUID id, AutorDTO dto);
    void delete(UUID id);

    AutorDTO findById(UUID id);
    List<AutorDTO> findAll();
   
    
 
}