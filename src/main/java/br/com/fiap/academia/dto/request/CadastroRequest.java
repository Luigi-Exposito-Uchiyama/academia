package br.com.fiap.academia.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record CadastroRequest(

        @Size(min = 2, max = 25)
        @NotNull(message = "Nome é Obrigatório")
        String atleta,


        @NotNull(message = "A Data de nascimento é Obrigatório")
        LocalDate nascimento,

        @Valid
        @NotNull(message = "O Login deve ser informado")
        LoginRequest login,

        List<TreinoRequest> treinos
) {
}