package com.projeto.pos.biblioteca.spring.controller;



import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.pos.biblioteca.spring.dto.EmprestimoDTO;
import com.projeto.pos.biblioteca.spring.service.EmprestimoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/emprestimos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Empréstimos", description = "Operações de empréstimo de livros")

public class EmprestimoController {

    private final EmprestimoService service;

    /* ---------- 1. CRIAR EMPRÉSTIMO ---------- */
    @PostMapping
    @Operation(summary = "Criar empréstimo (apenas exemplares disponíveis)")
    public ResponseEntity<EmprestimoDTO> create(@Valid @RequestBody EmprestimoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    /* ---------- 2. LISTAR TODOS (PAGINADO) ---------- */
    @GetMapping
    @Operation(summary = "Listar todos os empréstimos (paginado)")
    public ResponseEntity<Page<EmprestimoDTO>> listAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    /* ---------- 3. LISTAR POR USUÁRIO (PAGINADO) ---------- */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar empréstimos de um usuário (paginado)")
    public ResponseEntity<Page<EmprestimoDTO>> listByUsuario(@PathVariable UUID usuarioId, Pageable pageable) {
        return ResponseEntity.ok(service.findByUsuario(usuarioId, pageable));
    }

    /* ---------- 4. BUSCAR POR INTERVALO DE DATA (QUERY STRING) ---------- */
    @GetMapping("/intervalo")
    @Operation(summary = "Buscar empréstimos por intervalo de data (paginado)")
    public ResponseEntity<Page<EmprestimoDTO>> findByIntervalo(
            @Parameter(description = "Início do intervalo (ISO-8601)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Fim do intervalo (ISO-8601)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByIntervalo(inicio, fim, pageable));
    }

    /* ---------- 5. BUSCAR POR ID ---------- */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar empréstimo por ID")
    public ResponseEntity<EmprestimoDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /* ---------- 6. RENOVAR EMPRÉSTIMO ---------- */
    @PutMapping("/{id}/renovar")
    @Operation(summary = "Renovar empréstimo (status = RENOVADO)")
    public ResponseEntity<EmprestimoDTO> renovar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.renovar(id));
    }

    /* ---------- 7. FINALIZAR EMPRÉSTIMO ---------- */
    @PutMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar empréstimo (status = FINALIZADO)")
    public ResponseEntity<EmprestimoDTO> finalizar(@PathVariable UUID id) {
        return ResponseEntity.ok(service.finalizar(id));
    }

    /* ---------- 8. EXCLUIR EMPRÉSTIMO ---------- */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir empréstimo")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}