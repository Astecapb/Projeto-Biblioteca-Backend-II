package com.projeto.pos.biblioteca.spring.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.pos.biblioteca.spring.model.ExemplarLivro;
import com.projeto.pos.biblioteca.spring.model.ExemplarStatus;
import com.projeto.pos.biblioteca.spring.model.Livro;
import com.projeto.pos.biblioteca.spring.model.StatusConservacao;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    // busca por título utilizando query string (containing + ignore case)
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

  /* 2. Conta exemplares por status */
    @Query("SELECT COUNT(e) FROM ExemplarLivro e WHERE e.livro.id = :livroId AND e.status = :status")
    long countByLivroAndStatus(@Param("livroId") UUID livroId,
                               @Param("status") ExemplarStatus status);


    @Query("SELECT e FROM ExemplarLivro e WHERE e.statusConservacao = :conservacao")
List<ExemplarLivro> findByStatusConservacao(@Param("conservacao") StatusConservacao conservacao);



}
