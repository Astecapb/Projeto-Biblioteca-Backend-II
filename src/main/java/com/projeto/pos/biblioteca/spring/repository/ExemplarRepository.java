package com.projeto.pos.biblioteca.spring.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.pos.biblioteca.spring.model.ExemplarLivro;
import com.projeto.pos.biblioteca.spring.model.ExemplarStatus;
import com.projeto.pos.biblioteca.spring.model.Livro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/*public interface ExemplarRepository extends JpaRepository<ExemplarLivro, UUID> {
    long countByLivroId(UUID livroId);
    long countByLivroIdAndStatus(UUID livroId, ExemplarStatus status);

    public Object findByLivroId(UUID livroId);


Page<Emprestimo> findByUsuarioId(UUID usuarioId, Pageable pageable);

} */

public interface ExemplarRepository extends JpaRepository<ExemplarLivro, UUID> {

    // Buscar exemplares por livro
    List<ExemplarLivro> findByLivroId(UUID livroId);
    // Buscar exemplares por status
    List<ExemplarLivro> findByStatus(ExemplarStatus status);
    // Buscar por código único
    Optional<ExemplarLivro> findByCodigo(String codigo);
    

 /* ---------- para o count por status ---------- */
    @Query("SELECT e.status, COUNT(e) " +
           "FROM ExemplarLivro e " +
           "WHERE e.livro.id = :livroId " +
           "GROUP BY e.status")
    List<Object[]> countByStatusAndLivroId(@Param("livroId") UUID livroId);


}