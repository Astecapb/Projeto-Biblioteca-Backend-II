package com.projeto.pos.biblioteca.spring.dto;

import java.util.UUID;

import com.projeto.pos.biblioteca.spring.model.Autor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO {
    private UUID id;

    @NotBlank(message = "Nome do autor é obrigatório")
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    // conversor Entity -> DTO
    public static AutorDTO fromEntity(Autor autor) {
        return AutorDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .cpf(autor.getCpf())
                .email(autor.getEmail())
                .build();
    }

    // conversor DTO -> Entity
    public Autor toEntity() {
        Autor autor = new Autor();
        autor.setId(this.id);
        autor.setNome(this.nome);
        autor.setCpf(this.cpf);
        autor.setEmail(this.email);
        return autor;
    }
}
