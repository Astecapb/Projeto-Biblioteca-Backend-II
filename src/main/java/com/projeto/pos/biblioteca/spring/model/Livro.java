package com.projeto.pos.biblioteca.spring.model;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // ✅ correto para UUID
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    private String isbn;
    private String editora;
    private Integer ano;

    // ManyToMany unidirecional para Autor
    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<Autor> autores = new HashSet<>();

    // OneToMany para exemplares
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExemplarLivro> exemplares = new ArrayList<>();
}