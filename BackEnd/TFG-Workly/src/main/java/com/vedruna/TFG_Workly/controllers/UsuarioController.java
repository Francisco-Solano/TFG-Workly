package com.vedruna.TFG_Workly.controllers;

import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.UsuarioServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceI usuarioService;
    private final IUsuarioRepository userRepository;


    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfil() {
        return ResponseEntity.ok(usuarioService.obtenerPerfil());
    }

    @PutMapping("/perfil")
    public ResponseEntity<UsuarioDTO> actualizarPerfil(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> buscarPorEmailPath(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .map(u -> ResponseEntity.ok(new UsuarioDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/proyectos")
    public ResponseEntity<List<ProyectoDTO>> obtenerProyectosDelUsuario() {
        return ResponseEntity.ok(usuarioService.obtenerProyectosDelUsuario());
    }





}