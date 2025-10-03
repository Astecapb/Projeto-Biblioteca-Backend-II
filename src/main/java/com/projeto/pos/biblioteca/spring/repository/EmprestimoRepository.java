package com.projeto.pos.biblioteca.spring.repository;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.pos.biblioteca.spring.model.Emprestimo;


public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    Page<Emprestimo> findByDataHoraEmprestimoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    Page<Emprestimo> findByUsuarioId(UUID usuarioId, Pageable pageable);
}

/* 
public interface EmprestimoRepository extends JpaRepository<Emprestimo, UUID> {

    List<Emprestimo> findByUsuario(UsuarioBiblioteca usuario);

    List<Emprestimo> findByStatus(StatusEmprestimo status);
 
    List<Emprestimo> findByUsuarioAndStatus(UsuarioBiblioteca usuario, StatusEmprestimo status);
}*//* */


