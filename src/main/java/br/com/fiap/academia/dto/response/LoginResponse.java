package br.com.fiap.academia.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long id,
        String email
) {}
