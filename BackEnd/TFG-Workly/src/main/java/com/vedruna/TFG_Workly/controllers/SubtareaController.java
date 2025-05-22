package com.vedruna.TFG_Workly.controllers;

import com.vedruna.TFG_Workly.dto.CrearSubtareaDTO;
import com.vedruna.TFG_Workly.dto.SubtareaDTO;
import com.vedruna.TFG_Workly.services.SubtareaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subtareas")
@CrossOrigin(origins = "*")
public class SubtareaController {

    @Autowired
    private SubtareaServiceI subtareaService;

    // Crear una subtarea asociada a una tarea
    @PostMapping("/tarea/{tareaId}")
    public ResponseEntity<SubtareaDTO> crearSubtarea(
            @PathVariable Integer tareaId,
            @RequestBody CrearSubtareaDTO subtareaDTO
    ) {
        SubtareaDTO nuevaSubtarea = subtareaService.crearSubtarea(tareaId, subtareaDTO);
        return ResponseEntity.ok(nuevaSubtarea);
    }

    // Obtener una subtarea por su ID
    @GetMapping("/{subtareaId}")
    public ResponseEntity<SubtareaDTO> obtenerSubtareaPorId(@PathVariable Integer subtareaId) {
        return ResponseEntity.ok(subtareaService.obtenerSubtareaPorId(subtareaId));
    }

    // Actualizar una subtarea (título y/o estado)
    @PutMapping("/{subtareaId}")
    public ResponseEntity<SubtareaDTO> actualizarSubtarea(
            @PathVariable Integer subtareaId,
            @RequestBody SubtareaDTO subtareaDTO
    ) {
        return ResponseEntity.ok(subtareaService.actualizarSubtarea(subtareaId, subtareaDTO));
    }

    // Cambiar solo el estado de una subtarea
    /* YA LO HACE ACTUALIZAR SUBTAREA
    @PatchMapping("/{subtareaId}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Integer subtareaId,
            @RequestParam String estado
    ) {
        subtareaService.cambiarEstado(subtareaId, estado);
        return ResponseEntity.noContent().build();
    }

     */

    // Eliminar una subtarea
    @DeleteMapping("/{subtareaId}")
    public ResponseEntity<Void> eliminarSubtarea(@PathVariable Integer subtareaId) {
        subtareaService.eliminarSubtarea(subtareaId);
        return ResponseEntity.noContent().build();
    }

    // Listar todas las subtareas de una tarea
    @GetMapping("/tarea/{tareaId}")
    public ResponseEntity<List<SubtareaDTO>> listarSubtareasPorTarea(@PathVariable Integer tareaId) {
        return ResponseEntity.ok(subtareaService.listarSubtareasPorTarea(tareaId));
    }
}
