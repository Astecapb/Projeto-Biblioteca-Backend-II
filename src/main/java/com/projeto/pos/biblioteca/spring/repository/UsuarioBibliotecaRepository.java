package com.projeto.pos.biblioteca.spring.repository;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.pos.biblioteca.spring.model.UsuarioBiblioteca;

@Repository
//public interface UsuarioRepository extends JpaRepository<UsuarioBiblioteca, UUID> {
   // Optional<UsuarioBiblioteca> findByCpf(String cpf);
//}

public interface UsuarioBibliotecaRepository extends JpaRepository<UsuarioBiblioteca, UUID> {

    // Buscar usuário por email
    Optional<UsuarioBiblioteca> findByEmail(String email);

    // Buscar usuário por CPF
    Optional<UsuarioBiblioteca> findByCpf(String cpf);

    // Verificar se email já existe
    boolean existsByEmail(String email);

    // Verificar se CPF já existe
    boolean existsByCpf(String cpf);
}
