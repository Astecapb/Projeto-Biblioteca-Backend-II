package com.projeto.pos.biblioteca.spring.service;

import com.projeto.pos.biblioteca.spring.dto.UsuarioDTO;
import com.projeto.pos.biblioteca.spring.model.UsuarioBiblioteca;
import com.projeto.pos.biblioteca.spring.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioDTO create(UsuarioDTO dto) {
        if (repository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        UsuarioBiblioteca entity = UsuarioBiblioteca.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .cpf(dto.getCpf())
                .build();
        return UsuarioDTO.fromEntity(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(UsuarioDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(UUID id) {
        return repository.findById(id)
                .map(UsuarioDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public UsuarioDTO update(UUID id, UsuarioDTO dto) {
        UsuarioBiblioteca entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCpf(dto.getCpf());
        return UsuarioDTO.fromEntity(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }
}
