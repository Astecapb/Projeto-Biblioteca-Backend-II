package com.projeto.pos.biblioteca.spring.service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.pos.biblioteca.spring.dto.LivroDTO;
import com.projeto.pos.biblioteca.spring.model.Autor;
import com.projeto.pos.biblioteca.spring.model.ExemplarStatus;
import com.projeto.pos.biblioteca.spring.model.Livro;
import com.projeto.pos.biblioteca.spring.repository.AutorRepository;
import com.projeto.pos.biblioteca.spring.repository.LivroRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    /* ---------- 1. CRIAÇÃO ---------- */
    @Override
    @Transactional
    public LivroDTO create(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAno(dto.getAno());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());

        // associa autores existentes
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            Set<Autor> autores = autorRepository.findAllById(dto.getAutoresIds())
                                                .stream()
                                                .collect(Collectors.toSet());
            livro.setAutores(autores);
        }

        Livro saved = livroRepository.save(livro);
        return toDTO(saved);
    }

    /* ---------- 2. ATUALIZAÇÃO ---------- */
    @Override
    @Transactional
    public LivroDTO update(UUID id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        livro.setTitulo(dto.getTitulo());
        livro.setAno(dto.getAno());
        livro.setIsbn(dto.getIsbn());
        livro.setEditora(dto.getEditora());

        // re-associa autores
        if (dto.getAutoresIds() != null) {
            Set<Autor> autores = autorRepository.findAllById(dto.getAutoresIds())
                                                .stream()
                                                .collect(Collectors.toSet());
            livro.setAutores(autores);
        }

        return toDTO(livro);
    }

    /* ---------- 3. EXCLUSÃO ---------- */
    @Override
    @Transactional
    public void delete(UUID id) {
        if (!livroRepository.existsById(id)) {
            throw new EntityNotFoundException("Livro não encontrado");
        }
        livroRepository.deleteById(id);
    }

    /* ---------- 4. BUSCA POR ID ---------- */
    @Override
    @Transactional(readOnly = true)
    public LivroDTO findById(UUID id) {
        return livroRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
    }

    /* ---------- 5. BUSCA POR TÍTULO (PAGINADA) ---------- */
    @Override
    @Transactional(readOnly = true)
    public Page<LivroDTO> findAll(String tituloFilter, Pageable pageable) {
        return tituloFilter != null && !tituloFilter.isBlank()
                ? livroRepository.findByTituloContainingIgnoreCase(tituloFilter, pageable).map(this::toDTO)
                : livroRepository.findAll(pageable).map(this::toDTO);
    }

   /* 6. CONTAGEM DE EXEMPLARES – usa ExemplarStatus */
@Override
public long countExemplaresByStatus(UUID livroId, String status) {
    if (!livroRepository.existsById(livroId))
        throw new EntityNotFoundException("Livro não encontrado");
    return livroRepository.countByLivroAndStatus(livroId, ExemplarStatus.valueOf(status));
}

/* 7. CONTAGEM POR TODOS OS STATUS */
@Override
public Object countByAllStatuses(UUID livroId) {
    if (!livroRepository.existsById(livroId))
        throw new EntityNotFoundException("Livro não encontrado");

    return Map.of(
            "DISPONIVEL", livroRepository.countByLivroAndStatus(livroId, ExemplarStatus.DISPONIVEL),
            "EMPRESTADO", livroRepository.countByLivroAndStatus(livroId, ExemplarStatus.EMPRESTADO),
            "RESERVADO",  livroRepository.countByLivroAndStatus(livroId, ExemplarStatus.RESERVADO)
    );
}

    /* ---------- UTILITÁRIO: ENTIDADE → DTO ---------- */
    private LivroDTO toDTO(Livro l) {
        LivroDTO dto = new LivroDTO();
        dto.setId(l.getId());
        dto.setTitulo(l.getTitulo());
        dto.setAno(l.getAno());
        dto.setIsbn(l.getIsbn());
        dto.setEditora(l.getEditora());
        dto.setAutoresIds(
                l.getAutores().stream()
                        .map(Autor::getId)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    @Override
    public long countExemplares(UUID livroId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}