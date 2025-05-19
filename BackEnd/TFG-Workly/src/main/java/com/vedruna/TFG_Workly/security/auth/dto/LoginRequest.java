package com.vedruna.TFG_Workly.security.auth.dto;
/**
 * Los datos necesarios para registrarse
 * @param username
 * @param password
 */
public record LoginRequest(String username, String password) {
}
