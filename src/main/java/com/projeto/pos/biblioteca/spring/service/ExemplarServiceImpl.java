package com.projeto.pos.biblioteca.spring.service;

import com.projeto.pos.biblioteca.spring.dto.ExemplarDTO;
import com.projeto.pos.biblioteca.spring.model.ExemplarLivro;
import com.projeto.pos.biblioteca.spring.model.Livro;
import com.projeto.pos.biblioteca.spring.repository.ExemplarRepository;
import com.projeto.pos.biblioteca.spring.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExemplarServiceImpl implements ExemplarService {

    private final ExemplarRepository exemplarRepository;
    private final LivroRepository livroRepository;

    @Override
    @Transactional
    public ExemplarDTO create(ExemplarDTO dto) {
        Livro livro = livroRepository.findById(dto.getLivroId())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        ExemplarLivro exemplar = ExemplarLivro.builder()
                .codigo(dto.getCodigo())
                .status(dto.getStatus())
                .livro(livro)
                .build();

        return ExemplarDTO.fromEntity(exemplarRepository.save(exemplar));
    }

    @Override
    @Transactional
    public ExemplarDTO update(UUID id, ExemplarDTO dto) {
        ExemplarLivro exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exemplar não encontrado"));

        exemplar.setCodigo(dto.getCodigo());
        exemplar.setStatus(dto.getStatus());

        if (!exemplar.getLivro().getId().equals(dto.getLivroId())) {
            Livro livro = livroRepository.findById(dto.getLivroId())
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
            exemplar.setLivro(livro);
        }

        return ExemplarDTO.fromEntity(exemplarRepository.save(exemplar));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!exemplarRepository.existsById(id)) {
            throw new EntityNotFoundException("Exemplar não encontrado");
        }
        exemplarRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ExemplarDTO findById(UUID id) {
        return exemplarRepository.findById(id)
                .map(ExemplarDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Exemplar não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExemplarDTO> findByLivroId(UUID livroId) {
        return exemplarRepository.findByLivroId(livroId)
                .stream()
                .map(ExemplarDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExemplarDTO> findAll() {
        return exemplarRepository.findAll()
                .stream()
                .map(ExemplarDTO::fromEntity)
                .toList();
    }
}