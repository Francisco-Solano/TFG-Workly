package com.vedruna.TFG_Workly.security.auth.dto;

public record UsuarioResponseDTO(
        Integer usuarioId,
        String username,
        String email,
        String rol
) {}
