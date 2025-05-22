package com.vedruna.TFG_Workly.controllers;


import com.vedruna.TFG_Workly.dto.CrearEtiquetaDTO;
import com.vedruna.TFG_Workly.dto.EtiquetaDTO;
import com.vedruna.TFG_Workly.services.EtiquetaServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/etiquetas")
@RequiredArgsConstructor
public class EtiquetaController {

    private final EtiquetaServiceI etiquetaService;

    @PostMapping("/crear")
    public ResponseEntity<EtiquetaDTO> crearEtiqueta(@RequestBody CrearEtiquetaDTO etiquetaDTO) {
        return ResponseEntity.ok(etiquetaService.crearEtiqueta(etiquetaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtiquetaDTO> obtenerEtiquetaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(etiquetaService.obtenerEtiquetaPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EtiquetaDTO>> listarEtiquetas() {
        return ResponseEntity.ok(etiquetaService.listarEtiquetas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EtiquetaDTO> actualizarEtiqueta(@PathVariable Integer id, @RequestBody EtiquetaDTO etiquetaDTO) {
        return ResponseEntity.ok(etiquetaService.actualizarEtiqueta(id, etiquetaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEtiqueta(@PathVariable Integer id) {
        etiquetaService.eliminarEtiqueta(id);
        return ResponseEntity.noContent().build();
    }
/* ESTO LO HACE LOS ENDPOINT DE TAREA
    @PostMapping("/{etiquetaId}/asignar/{tareaId}")
    public ResponseEntity<Void> asignarEtiquetaATarea(@PathVariable Integer etiquetaId, @PathVariable Integer tareaId) {
        etiquetaService.asignarEtiquetaATarea(etiquetaId, tareaId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{etiquetaId}/quitar/{tareaId}")
    public ResponseEntity<Void> quitarEtiquetaDeTarea(@PathVariable Integer etiquetaId, @PathVariable Integer tareaId) {
        etiquetaService.quitarEtiquetaDeTarea(etiquetaId, tareaId);
        return ResponseEntity.ok().build();
    }

 */
}
