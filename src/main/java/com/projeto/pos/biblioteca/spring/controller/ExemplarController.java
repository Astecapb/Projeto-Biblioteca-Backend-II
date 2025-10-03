package com.projeto.pos.biblioteca.spring.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.pos.biblioteca.spring.dto.ExemplarDTO;
import com.projeto.pos.biblioteca.spring.service.ExemplarService;

@RestController
@RequestMapping("/exemplares")
public class ExemplarController {

    private final ExemplarService exemplarService;

    public ExemplarController(ExemplarService exemplarService) {
        this.exemplarService = exemplarService;
    }

    @PostMapping
    public ExemplarDTO create(@RequestBody ExemplarDTO dto) {
        return exemplarService.create(dto);
    }

    @GetMapping
    public List<ExemplarDTO> findAll() {
        return exemplarService.findAll();
    }

    @GetMapping("/{id}")
    public ExemplarDTO findById(@PathVariable UUID id) {
        return exemplarService.findById(id);
    }

    @PutMapping("/{id}")
    public ExemplarDTO update(@PathVariable UUID id, @RequestBody ExemplarDTO dto) {
        return exemplarService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        exemplarService.delete(id);
    }
}
