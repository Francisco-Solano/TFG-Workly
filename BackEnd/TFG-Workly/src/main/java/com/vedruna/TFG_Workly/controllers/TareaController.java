package com.vedruna.TFG_Workly.controllers;

import com.vedruna.TFG_Workly.dto.CrearTareaDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.services.TareaServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaServiceI tareaService;

    @PostMapping("/crear")
    public ResponseEntity<TareaDTO> crearTarea(@Valid @RequestBody CrearTareaDTO crearTareaDTO) {
        return ResponseEntity.ok(tareaService.crearTarea(crearTareaDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerTareaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(tareaService.obtenerTareaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizarTarea(@PathVariable Integer id, @RequestBody CrearTareaDTO tareaDTO) {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, tareaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Integer id) {
        tareaService.eliminarTarea(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/mover/{tablaId}")
    public ResponseEntity<Void> moverTarea(@PathVariable Integer id, @PathVariable Integer tablaId) {
        tareaService.moverTarea(id, tablaId);
        return ResponseEntity.ok().build();
    }
/*Se puede hacer en actualizar tarea
    @PostMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer id, @RequestParam String estado) {
        tareaService.cambiarEstado(id, estado);
        return ResponseEntity.ok().build();
    }

 */

    /* Se puede hacer en actualizar tarea
    @PostMapping("/{id}/prioridad")
    public ResponseEntity<Void> cambiarPrioridad(@PathVariable Integer id, @RequestParam String prioridad) {
        tareaService.cambiarPrioridad(id, prioridad);
        return ResponseEntity.ok().build();
    }

     */

    @PostMapping("/{id}/etiquetas/{etiquetaId}")
    public ResponseEntity<Void> añadirEtiqueta(@PathVariable Integer id, @PathVariable Integer etiquetaId) {
        tareaService.añadirEtiqueta(id, etiquetaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/etiquetas/{etiquetaId}")
    public ResponseEntity<Void> quitarEtiqueta(@PathVariable Integer id, @PathVariable Integer etiquetaId) {
        tareaService.quitarEtiqueta(id, etiquetaId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/asignar/{usuarioId}")
    public ResponseEntity<Void> asignarUsuario(@PathVariable Integer id, @PathVariable Integer usuarioId) {
        tareaService.asignarUsuario(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/desasignar/{usuarioId}")
    public ResponseEntity<Void> desasignarUsuario(@PathVariable Integer id, @PathVariable Integer usuarioId) {
        tareaService.desasignarUsuario(id, usuarioId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/fechaLimite")
    public ResponseEntity<Void> actualizarFechaLimite(@PathVariable Integer id, @RequestParam Date fechaLimite) {
        tareaService.actualizarFechaLimite(id, fechaLimite);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tabla/{tablaId}")
    public ResponseEntity<List<TareaDTO>> listarTareasPorTabla(@PathVariable Integer tablaId) {
        return ResponseEntity.ok(tareaService.listarTareasPorTabla(tablaId));
    }

    @GetMapping("/asignadas")
    public ResponseEntity<List<TareaDTO>> listarTareasAsignadasAlUsuario() {
        return ResponseEntity.ok(tareaService.listarTareasAsignadasAlUsuario());
    }
}

