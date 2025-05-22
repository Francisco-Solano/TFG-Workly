package com.vedruna.TFG_Workly.controllers;


import com.vedruna.TFG_Workly.dto.ArchivoDTO;
import com.vedruna.TFG_Workly.services.ArchivoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/archivos")
public class ArchivoController {

    @Autowired
    private ArchivoServiceI archivoService;

    /**
     * Subir un archivo y asociarlo a una tarea
     */
    @PostMapping("/tarea/{tareaId}")
    public ResponseEntity<ArchivoDTO> subirArchivo(
            @PathVariable Integer tareaId,
            @RequestParam("archivo") MultipartFile archivo) {

        ArchivoDTO archivoSubido = archivoService.subirArchivo(tareaId, archivo);
        return ResponseEntity.ok(archivoSubido);
    }

    /**
     * Eliminar un archivo por su ID
     */
    @DeleteMapping("/{archivoId}")
    public ResponseEntity<Void> eliminarArchivo(@PathVariable Integer archivoId) {
        archivoService.eliminarArchivo(archivoId);
        return ResponseEntity.noContent().build();
    }
}