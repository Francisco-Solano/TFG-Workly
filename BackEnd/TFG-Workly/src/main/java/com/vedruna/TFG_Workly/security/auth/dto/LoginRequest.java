package com.vedruna.TFG_Workly.security.auth.dto;
/**
 * Los datos necesarios para registrarse
 * @param email
 * @param password
 */
public record LoginRequest(String email, String password) {
}
