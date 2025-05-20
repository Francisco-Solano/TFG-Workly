package com.vedruna.TFG_Workly.security.auth.controllers;

import com.vedruna.TFG_Workly.models.Rol;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IRolRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.security.auth.dto.UsuarioResponseDTO;
import com.vedruna.TFG_Workly.security.jwt.JwtTokenProvider;
import com.vedruna.TFG_Workly.security.auth.dto.LoginRequest;
import com.vedruna.TFG_Workly.security.auth.dto.LoginResponse;
import com.vedruna.TFG_Workly.security.auth.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public UsuarioResponseDTO register(@RequestBody UserRegisterDTO userDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(userDTO.email());
        usuario.setUsername(userDTO.username());
        usuario.setPassword(passwordEncoder.encode(userDTO.password()));

        Rol rolUser = rolRepository.findByRolName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setUserRol(rolUser);

        Usuario savedUser = usuarioRepository.save(usuario);

        // Devolver solo lo necesario, en forma de DTO
        return new UsuarioResponseDTO(
                savedUser.getUsuarioId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getUserRol().getRolName()
        );
    }



    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO){
        Authentication authInputToken =
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());

        Authentication authentication = this.authManager.authenticate(authInputToken);
        Usuario user = (Usuario) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(authentication);

        return new LoginResponse(
                user.getEmail(),
                List.of(user.getUserRol().getRolName()), // Lista de roles como string
                token
        );
    }
}
