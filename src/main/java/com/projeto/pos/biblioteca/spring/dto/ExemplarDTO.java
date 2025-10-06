package com.projeto.pos.biblioteca.spring.dto;

import com.projeto.pos.biblioteca.spring.model.ExemplarLivro;
import com.projeto.pos.biblioteca.spring.model.ExemplarStatus;
import com.projeto.pos.biblioteca.spring.model.StatusConservacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExemplarDTO {

    private UUID id;

    @NotBlank(message = "Código do exemplar é obrigatório")
    private String codigo;

    @NotNull(message = "Status é obrigatório")
    private ExemplarStatus status;

    @NotNull(message = "Conservação é obrigatória")
    private StatusConservacao statusConservacao;

    @NotNull(message = "livroId é obrigatório")
    private UUID livroId;

    /* ---------- conversão ---------- */
    public static ExemplarDTO fromEntity(ExemplarLivro entity) {
        return ExemplarDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .status(entity.getStatus())
                .statusConservacao(entity.getStatusConservacao())
                .livroId(entity.getLivro().getId())
                .build();
    }
}