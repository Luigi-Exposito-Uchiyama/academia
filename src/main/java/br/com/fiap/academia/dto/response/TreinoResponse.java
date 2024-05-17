package br.com.fiap.academia.dto.response;

import lombok.Builder;

@Builder
public record TreinoResponse(
        Long id,
        String exercicio,
        int series,
        int repeticoes,
        double carga,
        int tempoDescanso
) {}
