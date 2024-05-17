package br.com.fiap.academia.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CadastroResponse(
        Long id,
        String atleta,
        LoginResponse login,
        LocalDate nascimento
) {}
