package com.vedruna.TFG_Workly.security.auth.dto;


/**
 * Los datos necesarios para registrarse
 * @param username
 * @param email
 * @param password
 * @param password2
 */
public record UserRegisterDTO(String username, String email, String password, String password2) {
}
