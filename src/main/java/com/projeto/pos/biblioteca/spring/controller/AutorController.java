package com.projeto.pos.biblioteca.spring.controller;


import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.pos.biblioteca.spring.dto.AutorDTO;
import com.projeto.pos.biblioteca.spring.service.AutorService;

import jakarta.validation.Valid;

/**
 * Controller para CRUD de Autores
 */

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
@Validated

public class AutorController {

    private final AutorService autorService;   // preferência: injeção por construtor

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    /* ---------- CRUD ---------- */

    @PostMapping
    public ResponseEntity<AutorDTO> create(@Valid @RequestBody AutorDTO dto) {
        AutorDTO created = autorService.create(dto);
        return ResponseEntity.created(URI.create("/api/autores/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")          // único método PUT
    public ResponseEntity<AutorDTO> update(@PathVariable UUID id,
                                           @Valid @RequestBody AutorDTO dto) {
        AutorDTO updated = autorService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        autorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(autorService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> listAll() {
        return ResponseEntity.ok(autorService.findAll());
    }
}



