package com.vedruna.TFG_Workly.security.auth.services;


import com.vedruna.TFG_Workly.security.auth.dto.AuthResponseDTO;
import com.vedruna.TFG_Workly.security.auth.dto.LoginRequestDTO;
import com.vedruna.TFG_Workly.security.auth.dto.RegisterRequestDTO;

public interface AuthServiceI {
    AuthResponseDTO login(LoginRequestDTO request);
    void register(RegisterRequestDTO request);
}
