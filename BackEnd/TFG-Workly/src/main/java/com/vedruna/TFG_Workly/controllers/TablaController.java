package com.vedruna.TFG_Workly.controllers;


import com.vedruna.TFG_Workly.dto.CrearTablaDTO;
import com.vedruna.TFG_Workly.dto.TablaDTO;
import com.vedruna.TFG_Workly.services.ProyectoServiceI;
import com.vedruna.TFG_Workly.services.TablaServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tablas")
@RequiredArgsConstructor
public class TablaController {

    private final TablaServiceI tablaService;
    private final ProyectoServiceI proyectoService;

    @PostMapping("/crear")
    public ResponseEntity<TablaDTO> crearTabla(@RequestBody @Valid CrearTablaDTO crearTablaDTO) {
        TablaDTO tablaCreada = tablaService.crearTabla(crearTablaDTO.getIdProyecto(), crearTablaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tablaCreada);
    }


    @GetMapping("/{tablaId}")
    public ResponseEntity<TablaDTO> obtenerTabla(@PathVariable Integer tablaId) {
        return ResponseEntity.ok(tablaService.obtenerTablaPorId(tablaId));
    }

    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<List<TablaDTO>> listarTablasPorProyecto(@PathVariable Integer proyectoId) {
        return ResponseEntity.ok(tablaService.listarTablasPorProyecto(proyectoId));
    }

    @PutMapping("/{tablaId}")
    public ResponseEntity<TablaDTO> actualizarTabla(@PathVariable Integer tablaId, @RequestBody TablaDTO tablaDTO) {
        return ResponseEntity.ok(tablaService.actualizarTabla(tablaId, tablaDTO));
    }

    @PatchMapping("/{tablaId}/posicion")
    public ResponseEntity<Void> cambiarPosicion(@PathVariable Integer tablaId, @RequestParam int posicion) {
        tablaService.cambiarPosicion(tablaId, posicion);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tablaId}")
    public ResponseEntity<Void> eliminarTabla(@PathVariable Integer tablaId) {
        tablaService.eliminarTabla(tablaId);
        return ResponseEntity.noContent().build();
    }
}
