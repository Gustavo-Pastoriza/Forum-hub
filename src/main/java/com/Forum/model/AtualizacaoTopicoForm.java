package com.Forum.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizacaoTopicoForm {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A mensagem é obrigatória")
    private String mensagem;

    @NotNull(message = "O ID do usuário é obrigatório para atualizar o tópico") // Tornar usuarioId obrigatório
    private Long usuarioId;

    @NotNull(message = "O ID do curso é obrigatório para atualizar o tópico")   // Tornar cursoId obrigatório
    private Long cursoId;
}