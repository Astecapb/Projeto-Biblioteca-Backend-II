package com.projeto.pos.biblioteca.spring.repository;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.pos.biblioteca.spring.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    // Buscar autor por nome (ignora maiúsculas/minúsculas)
    List<Autor> findByNomeContainingIgnoreCase(String nome);

    boolean existsByCpf(@NotBlank(message = "CPF é obrigatório") String cpf);
    boolean existsByEmail(@NotBlank(message = "Email é obrigatório")String email);
}
