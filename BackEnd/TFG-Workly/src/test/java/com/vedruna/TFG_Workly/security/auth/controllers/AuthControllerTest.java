package com.vedruna.TFG_Workly.security.auth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vedruna.TFG_Workly.models.Rol;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IRolRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.security.auth.dto.LoginRequest;
import com.vedruna.TFG_Workly.security.auth.dto.UserRegisterDTO;
import com.vedruna.TFG_Workly.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsuarioRepository usuarioRepository;

    @MockBean
    private IRolRepository rolRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Registro de usuario exitoso")
    void testRegisterUserSuccessfully() throws Exception {
        UserRegisterDTO userDTO = new UserRegisterDTO("testuser", "test@example.com", "password", "password");

        Rol mockRol = new Rol();
        mockRol.setRolName("ROLE_USER");

        Usuario savedUser = new Usuario();
        savedUser.setUsuarioId(1);
        savedUser.setUsername("test@example.com"); // <--- Aquí asumimos que username == email en la respuesta real
        savedUser.setEmail("test@example.com");
        savedUser.setUserRol(mockRol);

        Mockito.when(rolRepository.findByRolName("ROLE_USER")).thenReturn(Optional.of(mockRol));
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        Mockito.when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUser);

        mockMvc.perform(post("/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.username").value("test@example.com")) // <--- Ajustado a respuesta real
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    @Test
    @DisplayName("Login exitoso")
    void testLoginSuccessfully() throws Exception {
        LoginRequest loginDTO = new LoginRequest("test@example.com", "password");

        Usuario user = new Usuario();
        user.setEmail("test@example.com");

        Rol rol = new Rol();
        rol.setRolName("ROLE_USER");
        user.setUserRol(rol);

        Authentication mockAuth = new UsernamePasswordAuthenticationToken(user, null);

        Mockito.when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        Mockito.when(jwtTokenProvider.generateToken(mockAuth)).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.authorities[0]").value("ROLE_USER")) // <-- Asumiendo que backend devuelve authorities
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    @DisplayName("Login fallido")
    void testLoginFailure() throws Exception {
        LoginRequest loginDTO = new LoginRequest("test@example.com", "wrong-password");

        Mockito.when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Authentication failed"));

        mockMvc.perform(post("/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof RuntimeException &&
                                result.getResolvedException().getMessage().equals("Authentication failed")
                ));
    }


}
