package br.com.fiap.academia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TreinoRequest(
        @NotBlank(message = "Exercício não pode estar em branco")
        String exercicio,

        @NotNull(message = "Número de séries é obrigatório")
        int series,

        @NotNull(message = "Número de repetições é obrigatório")
        int repeticoes,

        @NotNull(message = "Carga é obrigatória")
        double carga,

        @NotNull(message = "Tempo de descanso é obrigatório")
        int tempoDescanso
) {}
