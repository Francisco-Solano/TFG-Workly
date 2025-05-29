package com.vedruna.TFG_Workly.controllers;

import com.vedruna.TFG_Workly.dto.CrearProyectoDTO;
import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Colaborador;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IColaboradorRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.ProyectoServiceI;
import com.vedruna.TFG_Workly.services.UsuarioServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/proyectos")
@RequiredArgsConstructor
public class ProyectoController {


    private final ProyectoServiceI proyectoService;

    private final UsuarioServiceI usuarioService;

    private final IUsuarioRepository usuarioRepository;

    private final IColaboradorRepository colaboradorRepository;



    @GetMapping("/compartidos/{usuarioId}")
    public ResponseEntity<List<ProyectoDTO>> obtenerProyectosCompartidos(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(proyectoService.obtenerProyectosComoColaborador(usuarioId));
    }


    @PostMapping("/crear")
    public ResponseEntity<ProyectoDTO> crearProyecto(
            @Valid @RequestBody CrearProyectoDTO crearProyectoDTO,
            Principal principal) {
        // Obtener email o username del usuario autenticado
        String emailUsuario = principal.getName();

        // Buscar el usuario en la base de datos

        Usuario usuario = usuarioRepository
                .findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));



        // Crear proyecto pasando el DTO y el usuario
        ProyectoDTO nuevoProyecto = proyectoService.crearProyecto(crearProyectoDTO, usuario);

        return ResponseEntity.ok(nuevoProyecto);
    }







    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDTO> obtenerProyecto(@PathVariable Integer id) {
        ProyectoDTO proyecto = proyectoService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(proyecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDTO> actualizarProyecto(@PathVariable Integer id, @RequestBody ProyectoDTO proyectoDTO) {
        ProyectoDTO actualizado = proyectoService.actualizarProyecto(id, proyectoDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Integer id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mios")
    public ResponseEntity<List<ProyectoDTO>> listarProyectosUsuario() {
        return ResponseEntity.ok(proyectoService.listarProyectosUsuario());
    }



    @PutMapping("/{id}/visibilidad")
    public ResponseEntity<ProyectoDTO> cambiarVisibilidad(@PathVariable Integer id, @RequestParam boolean publica) {
        return ResponseEntity.ok(proyectoService.cambiarVisibilidad(id, publica));
    }



    @GetMapping("/publicos")
    public ResponseEntity<List<ProyectoDTO>> buscarPublicos() {
        return ResponseEntity.ok(proyectoService.buscarProyectosPublicos());
    }

    @GetMapping("/privados")
    public ResponseEntity<List<ProyectoDTO>> buscarPrivados() {
        return ResponseEntity.ok(proyectoService.buscarProyectosPrivados());
    }

    @PostMapping("/{id}/colaboradores")
    public ResponseEntity<Void> añadirColaborador(
            @PathVariable Integer id,
            @RequestParam Integer usuarioId,
            @RequestParam String rol
    ) {
        proyectoService.añadirColaborador(id, usuarioId, rol);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/colaboradores/{usuarioId}")
    public ResponseEntity<Void> eliminarColaborador(
            @PathVariable Integer id,
            @PathVariable Integer usuarioId
    ) {
        proyectoService.eliminarColaborador(id, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/colaboradores")
    public ResponseEntity<List<UsuarioDTO>> listarColaboradores(@PathVariable Integer id) {
        return ResponseEntity.ok(proyectoService.listarColaboradores(id));
    }

    @PutMapping("/{proyectoId}/colaboradores/{usuarioId}/rol")
    public ResponseEntity<Void> asignarRolAColaborador(
            @PathVariable Integer proyectoId,
            @PathVariable Integer usuarioId,
            @RequestParam String nuevoRol
    ) {
        proyectoService.asignarRol(proyectoId, usuarioId, nuevoRol);
        return ResponseEntity.ok().build();
    }



}


