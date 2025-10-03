package com.projeto.pos.biblioteca.spring.dto;


import java.util.UUID;

import com.projeto.pos.biblioteca.spring.model.UsuarioBiblioteca;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioDTO {

    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120)
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 14)
    private String cpf;

    public static UsuarioDTO fromEntity(UsuarioBiblioteca entity) {
        return UsuarioDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .cpf(entity.getCpf())
                .build();
    }
}
