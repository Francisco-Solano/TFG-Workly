package com.vedruna.TFG_Workly.security.auth.controllers;

import com.vedruna.TFG_Workly.exceptions.RolNotFoundException;
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

    //Metodo para registrar un usuario
    @PostMapping("/register")
    public UsuarioResponseDTO register(@RequestBody UserRegisterDTO userDTO) {
        //Creamos un usuario
        Usuario usuario = new Usuario();
        //Seteamos los valores que ha escrito el usuario
        usuario.setEmail(userDTO.email());
        usuario.setUsername(userDTO.username());
        usuario.setPassword(passwordEncoder.encode(userDTO.password()));

        Rol rolUser = rolRepository.findByRolName("ROLE_USER")
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));

        usuario.setUserRol(rolUser);
        //Guardamos el usuario
        Usuario savedUser = usuarioRepository.save(usuario);

        //Devolvemos el usuario creado con todos los datos
        return new UsuarioResponseDTO(
                savedUser.getUsuarioId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getUserRol().getRolName()
        );
    }


    //Loguear un usuario
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO){
        //Creamos un Authentication token
        Authentication authInputToken =
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());
        //Autenticamos al usuario
        Authentication authentication = this.authManager.authenticate(authInputToken);
        Usuario user = (Usuario) authentication.getPrincipal();

        //Creamos su token
        String token = jwtTokenProvider.generateToken(authentication);

        //Devolvemos el token junto con otros datoss si no hay errores.
        return new LoginResponse(
                user.getEmail(),
                List.of(user.getUserRol().getRolName()),
                token, user.getUsuarioId()
        );
    }
}
