package com.projeto.pos.biblioteca.spring.controller;

import com.projeto.pos.biblioteca.spring.dto.AutorDTO;
import com.projeto.pos.biblioteca.spring.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "CRUD de autores")
public class AutorController {

    private final AutorService service;

    /* ---------- CRUD ---------- */

    @PostMapping
    @Operation(summary = "Criar autor", description = "Cadastra um novo autor e devolve os dados completos")
    public ResponseEntity<AutorDTO> create(@Valid @RequestBody AutorDTO dto) {
        AutorDTO created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/autores/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor", description = "Atualiza todos os campos do autor")
    public ResponseEntity<AutorDTO> update(@PathVariable UUID id,
                                           @Valid @RequestBody AutorDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir autor", description = "Remove o autor pelo ID")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor", description = "Retorna os dados completos de um autor")
    public ResponseEntity<AutorDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar autores", description = "Retorna todos os autores cadastrados")
    public ResponseEntity<List<AutorDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }
}