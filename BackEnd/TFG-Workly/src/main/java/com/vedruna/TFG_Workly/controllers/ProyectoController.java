package com.vedruna.TFG_Workly.controllers;

import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.security.auth.dto.UserRegisterDTO;
import com.vedruna.TFG_Workly.security.auth.dto.UsuarioResponseDTO;
import com.vedruna.TFG_Workly.services.ProyectoServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoServiceI proyectoService;



    @PostMapping("/crear")
    public ResponseEntity<ProyectoDTO> crearProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        ProyectoDTO nuevo = proyectoService.crearProyecto(proyectoDTO);
        return ResponseEntity.ok(nuevo);
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
    public ResponseEntity<List<ProyectoDTO>> buscarPublicos(@RequestParam String nombre) {
        return ResponseEntity.ok(proyectoService.buscarProyectosPublicos(nombre));
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


}


