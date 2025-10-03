package com.projeto.pos.biblioteca.spring.dto;

import java.util.UUID;

import com.projeto.pos.biblioteca.spring.model.ExemplarLivro;
import com.projeto.pos.biblioteca.spring.model.ExemplarStatus;
import com.projeto.pos.biblioteca.spring.model.StatusConservacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para Exemplar.
 */
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ExemplarDTO {

    private UUID id;

    @NotBlank(message = "Código do exemplar é obrigatório")
    private String codigo;

    @NotNull(message = "Status é obrigatório")
    private ExemplarStatus status;

    @NotNull(message = "livroId é obrigatório")
    private UUID livroId;

   
    /* getters e setters */
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public ExemplarStatus getStatus() { return status; }
    public void setStatus(ExemplarStatus status) { this.status = status; }

    public UUID getLivroId() { return livroId; }
    public void setLivroId(UUID livroId) { this.livroId = livroId; }

    /* ---------- MÉTODO CORRIGIDO ---------- */
    public static ExemplarDTO fromEntity(ExemplarLivro entity) {
        ExemplarDTO dto = new ExemplarDTO();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setStatus(entity.getStatus());
        dto.setLivroId(entity.getLivro().getId());
        return dto;
    }

    private StatusConservacao statusConservacao;
}