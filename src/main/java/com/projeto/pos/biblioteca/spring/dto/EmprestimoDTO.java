package com.projeto.pos.biblioteca.spring.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import com.projeto.pos.biblioteca.spring.model.Emprestimo;
import com.projeto.pos.biblioteca.spring.model.StatusEmprestimo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmprestimoDTO {

    private UUID id;
    private LocalDateTime dataHoraEmprestimo;
    private LocalDateTime dataHoraDevolucao;
    private StatusEmprestimo status;
    private UUID usuarioId;
    private UUID exemplarId;

    public static EmprestimoDTO fromEntity(Emprestimo entity) {
        return EmprestimoDTO.builder()
                .id(entity.getId())
                .dataHoraEmprestimo(entity.getDataHoraEmprestimo())
                .dataHoraDevolucao(entity.getDataHoraDevolucao())
                .status(entity.getStatus())
                .usuarioId(entity.getUsuario().getId())
                .exemplarId(entity.getExemplar().getId())
                .build();
    }
}