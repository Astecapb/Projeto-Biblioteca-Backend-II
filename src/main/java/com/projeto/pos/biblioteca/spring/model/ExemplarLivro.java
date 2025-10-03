package com.projeto.pos.biblioteca.spring.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade ExemplarLivro representa cada cópia física/registro.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exemplar_livro")
public class ExemplarLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ correto para UUID (Hibernate 6+)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    // código único do exemplar (pode ser barcode)
    @Column(nullable = false, unique = true)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExemplarStatus status = ExemplarStatus.DISPONIVEL;

    // referência para o livro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_conservacao")
    private StatusConservacao statusConservacao;
}
