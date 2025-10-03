package com.projeto.pos.biblioteca.spring.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.projeto.pos.biblioteca.spring.dto.LivroDTO;
import com.projeto.pos.biblioteca.spring.service.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
@Validated
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroDTO> create(@Valid @RequestBody LivroDTO dto) {
        LivroDTO created = livroService.create(dto);
        return ResponseEntity.created(URI.create("/api/livros/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> update(@PathVariable UUID id, @Valid @RequestBody LivroDTO dto) {
        LivroDTO updated = livroService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(livroService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LivroDTO>> listAll(Pageable pageable) {
        return ResponseEntity.ok(livroService.findAll(null, pageable));
    }
}
