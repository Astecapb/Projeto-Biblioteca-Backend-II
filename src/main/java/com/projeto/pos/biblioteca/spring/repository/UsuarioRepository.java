package com.projeto.pos.biblioteca.spring.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.pos.biblioteca.spring.model.UsuarioBiblioteca;

public interface UsuarioRepository extends JpaRepository<UsuarioBiblioteca, UUID> {
    // opcional: métodos customizados
    
    // método necessário para o service
    boolean existsByCpf(String cpf);
}

