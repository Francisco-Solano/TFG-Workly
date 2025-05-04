package com.vedruna.TFG_Workly.security.auth.controllers;

import com.vedruna.TFG_Workly.security.auth.dto.AuthResponseDTO;
import com.vedruna.TFG_Workly.security.auth.dto.LoginRequestDTO;
import com.vedruna.TFG_Workly.security.auth.dto.RegisterRequestDTO;
import com.vedruna.TFG_Workly.security.auth.services.AuthServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthServiceI authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok(new AuthResponseDTO("User registered successfully"));
    }

}
