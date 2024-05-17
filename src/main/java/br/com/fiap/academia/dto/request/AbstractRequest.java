package br.com.fiap.academia.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(

        @Positive(message = "O Id deve ser um numero positivo")
        @NotNull(message = "O id deve ser informado")
        Long id

) {
}