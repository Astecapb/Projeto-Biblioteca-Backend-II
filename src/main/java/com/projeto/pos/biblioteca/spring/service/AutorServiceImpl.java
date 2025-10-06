package com.projeto.pos.biblioteca.spring.service;

import com.projeto.pos.biblioteca.spring.dto.AutorDTO;
import com.projeto.pos.biblioteca.spring.model.Autor;
import com.projeto.pos.biblioteca.spring.repository.AutorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AutorServiceImpl implements AutorService {

    private final AutorRepository repository;

    @Override
    public AutorDTO create(AutorDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            log.error("CPF já cadastrado: {}", dto.getCpf());
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        Autor entity = Autor.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .build();
        Autor saved = repository.save(entity);
        log.info("Autor criado: {}", saved.getId());
        return AutorDTO.fromEntity(saved);
    }

    @Override
    public AutorDTO update(UUID id, AutorDTO dto) {
        Autor entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        return AutorDTO.fromEntity(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Autor não encontrado");
        }
        repository.deleteById(id);
        log.info("Autor excluído: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AutorDTO findById(UUID id) {
        return repository.findById(id)
                .map(AutorDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutorDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(AutorDTO::fromEntity)
                .toList();
    }
}