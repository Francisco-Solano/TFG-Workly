package com.vedruna.TFG_Workly.security.auth.services;
import com.vedruna.TFG_Workly.models.Rol;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IRolRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.security.auth.dto.AuthResponseDTO;
import com.vedruna.TFG_Workly.security.auth.dto.LoginRequestDTO;
import com.vedruna.TFG_Workly.security.auth.dto.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements AuthServiceI {

    @Autowired
    private IUsuarioRepository userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private IRolRepository rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
        Usuario user=userRepo.findByNombre(request.getName()).orElseThrow();
        return new AuthResponseDTO(jwtService.getToken(user));
    }

    public void register(RegisterRequestDTO request) {
        Rol rol = rolRepo.findByRolName("USER")
                    .orElseThrow(() -> new RuntimeException("Rol not found"));
        Usuario user = new Usuario();
        user.setNombre(request.getName());
        user.setContraseña(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setUserRol(rol);
        userRepo.save(user);
    }
}
