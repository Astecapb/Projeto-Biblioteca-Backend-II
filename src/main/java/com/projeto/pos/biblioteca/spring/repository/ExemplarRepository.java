package com.projeto.pos.biblioteca.spring.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.pos.biblioteca.spring.model.ExemplarLivro;
import com.projeto.pos.biblioteca.spring.model.ExemplarStatus;
import com.projeto.pos.biblioteca.spring.model.Livro;


/*public interface ExemplarRepository extends JpaRepository<ExemplarLivro, UUID> {
    long countByLivroId(UUID livroId);
    long countByLivroIdAndStatus(UUID livroId, ExemplarStatus status);

    public Object findByLivroId(UUID livroId);


Page<Emprestimo> findByUsuarioId(UUID usuarioId, Pageable pageable);

} */

public interface ExemplarRepository extends JpaRepository<ExemplarLivro, UUID> {

    // Buscar exemplares por livro
    List<ExemplarLivro> findByLivro(Livro livro);

    // Buscar exemplares por status
    List<ExemplarLivro> findByStatus(ExemplarStatus status);

    // Buscar por código único
    ExemplarLivro findByCodigo(String codigo);
    
    List<ExemplarLivro> findByLivroId(UUID livroId);

}