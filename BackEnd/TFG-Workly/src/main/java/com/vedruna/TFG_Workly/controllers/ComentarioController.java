package com.vedruna.TFG_Workly.controllers;


import com.vedruna.TFG_Workly.dto.ComentarioDTO;
import com.vedruna.TFG_Workly.dto.CrearComentarioDTO;
import com.vedruna.TFG_Workly.services.ComentarioServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioServiceI comentarioService;

    @PostMapping("/tarea/{tareaId}")
    public ResponseEntity<ComentarioDTO> crearComentario(
            @PathVariable Integer tareaId,
            @RequestBody CrearComentarioDTO comentarioDTO) {
        return ResponseEntity.ok(comentarioService.crearComentario(tareaId, comentarioDTO));
    }

    @GetMapping("/{comentarioId}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable Integer comentarioId) {
        return ResponseEntity.ok(comentarioService.obtenerComentarioPorId(comentarioId));
    }

    @GetMapping("/tarea/{tareaId}")
    public ResponseEntity<List<ComentarioDTO>> listarComentariosPorTarea(@PathVariable Integer tareaId) {
        return ResponseEntity.ok(comentarioService.listarComentariosPorTarea(tareaId));
    }

    @PutMapping("/{comentarioId}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(
            @PathVariable Integer comentarioId,
            @RequestBody ComentarioDTO comentarioDTO) {
        return ResponseEntity.ok(comentarioService.actualizarComentario(comentarioId, comentarioDTO));
    }

    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Integer comentarioId) {
        comentarioService.eliminarComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }
}