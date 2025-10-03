package com.projeto.pos.biblioteca.spring.service;


import com.projeto.pos.biblioteca.spring.dto.EmprestimoDTO;
import com.projeto.pos.biblioteca.spring.model.*;
import com.projeto.pos.biblioteca.spring.repository.EmprestimoRepository;
import com.projeto.pos.biblioteca.spring.repository.ExemplarRepository;
import com.projeto.pos.biblioteca.spring.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Service
@RequiredArgsConstructor
@Transactional
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final ExemplarRepository exemplarRepository;
    private final UsuarioRepository usuarioRepository;

    /* ---------- 1. CRIAR EMPRÉSTIMO ---------- */
    @Transactional
    public EmprestimoDTO create(EmprestimoDTO dto) {
        // só empresta se disponível
        ExemplarLivro exemplar = exemplarRepository.findById(dto.getExemplarId())
                .orElseThrow(() -> new EntityNotFoundException("Exemplar não encontrado"));

        if (exemplar.getStatus() != ExemplarStatus.DISPONIVEL) {
            throw new IllegalArgumentException("Exemplar não está disponível para empréstimo");
        }

        UsuarioBiblioteca usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Emprestimo emp = Emprestimo.builder()
                .dataHoraEmprestimo(LocalDateTime.now())
                .status(StatusEmprestimo.ATIVO)
                .usuario(usuario)
                .exemplar(exemplar)
                .build();

        // marca exemplar como EMPRESTADO
        validaEAlteraStatusExemplar(exemplar, ExemplarStatus.EMPRESTADO);
       // exemplar.setStatus(StatusLivro.EMPRESTADO);

        return EmprestimoDTO.fromEntity(emprestimoRepository.save(emp));
    }

    /* ---------- 2. LISTAR EMPRÉSTIMOS (PAGINADO) ---------- */
    @Transactional(readOnly = true)
    public Page<EmprestimoDTO> findAll(Pageable pageable) {
        return emprestimoRepository.findAll(pageable).map(EmprestimoDTO::fromEntity);
    }

    /* ---------- 3. BUSCAR POR INTERVALO DE DATA ---------- */
    @Transactional(readOnly = true)
    public Page<EmprestimoDTO> findByIntervalo(LocalDateTime inicio, LocalDateTime fim, Pageable pageable) {
        return emprestimoRepository.findByDataHoraEmprestimoBetween(inicio, fim, pageable)
                .map(EmprestimoDTO::fromEntity);
    }

    /* ---------- 4. BUSCAR POR ID ---------- */
    @Transactional(readOnly = true)
    public EmprestimoDTO findById(UUID id) {
        return emprestimoRepository.findById(id)
                .map(EmprestimoDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));
    }

    /* ---------- 5. RENOVAR EMPRÉSTIMO ---------- */
    @Transactional
    public EmprestimoDTO renovar(UUID id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        if (emp.getStatus() != StatusEmprestimo.ATIVO) {
            throw new IllegalArgumentException("Só é possível renovar empréstimos ATIVOS");
        }

        emp.setStatus(StatusEmprestimo.RENOVADO);
        // você pode adicionar lógica de prorrogação de data aqui

        return EmprestimoDTO.fromEntity(emprestimoRepository.save(emp));
    }

    /* ---------- 6. FINALIZAR EMPRÉSTIMO ---------- */
    @Transactional
    public EmprestimoDTO finalizar(UUID id) {
        Emprestimo emp = emprestimoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado"));

        emp.setStatus(StatusEmprestimo.FINALIZADO);
        emp.setDataHoraDevolucao(LocalDateTime.now());

        // devolve exemplar
        
        validaEAlteraStatusExemplar(emp.getExemplar(), ExemplarStatus.DISPONIVEL);
        //emp.getExemplar().setStatus(StatusLivro.DISPONIVEL);

        return EmprestimoDTO.fromEntity(emprestimoRepository.save(emp));
    }

    /* ---------- 7. BUSCAR POR USUÁRIO (paginado) – falta no controller ---------- */
    @Transactional(readOnly = true)
    public Page<EmprestimoDTO> findByUsuario(UUID usuarioId, Pageable pageable) {
        return emprestimoRepository.findByUsuarioId(usuarioId, pageable)
                                   .map(EmprestimoDTO::fromEntity);
    }

    /* ---------- 8. EXCLUIR EMPRÉSTIMO ---------- */
    @Transactional
    public void delete(UUID id) {
        if (!emprestimoRepository.existsById(id)) {
            throw new EntityNotFoundException("Empréstimo não encontrado");
        }
        emprestimoRepository.deleteById(id);
    }

    /* ---------- 9. CORREÇÕES DE TIPO / ENUM ---------- */
    /*  Ajustes rápidos nos métodos create e finalizar para usar sempre ExemplarStatus
        (substitui StatusLivro que gerava incompatible types)           */

    private void validaEAlteraStatusExemplar(ExemplarLivro exemplar, ExemplarStatus novoStatus) {
        if (exemplar.getStatus() == novoStatus) {
            throw new IllegalArgumentException("Exemplar já possui o status solicitado");
        }
        exemplar.setStatus(novoStatus);
    }
}



