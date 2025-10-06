package com.projeto.pos.biblioteca.spring.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO para criação/edição de Livro.
 * Envia apenas os campos que existem no banco (titulo, isbn, editora, ano).
 * autoresIds = lista de IDs de autores já cadastrados.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LivroDTO {

    private UUID id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200)
    private String titulo;
    @Size(max = 20)
    private String isbn;
    @Size(max = 100)
    private String editora;
    private Integer ano;

    private List<UUID> autoresIds = new ArrayList<>();


    /* ---------- getters / setters ---------- */
  
    @Override
    public String toString() {
        return "LivroDTO{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", isbn='" + isbn + '\'' +
               ", editora='" + editora + '\'' +
               ", ano=" + ano +
               ", autoresIds=" + autoresIds +
               '}';
    }
}