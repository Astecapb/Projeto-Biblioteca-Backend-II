package com.projeto.pos.biblioteca.spring.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Hibernate 6+ (Spring Boot 3.x)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dataHoraEmprestimo;

    private LocalDateTime dataHoraDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEmprestimo status;

    /* Unidirecional: Emprestimo → Usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioBiblioteca usuario;

    /* Unidirecional: Emprestimo → Exemplar */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exemplar_id", nullable = false)
    private ExemplarLivro exemplar;
}
