package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ArchivoDTO;
import com.vedruna.TFG_Workly.models.Archivo;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.repositories.IArchivoRepository;
import com.vedruna.TFG_Workly.repositories.ITareaRepository;
import com.vedruna.TFG_Workly.services.ArchivoServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArchivoServiceImpl implements ArchivoServiceI {
    @Autowired
    ITareaRepository tareaRepository;

    @Autowired
    IArchivoRepository archivoRepository;


    @Override
    public ArchivoDTO subirArchivo(Integer tareaId, MultipartFile archivo) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        if (archivo.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío.");
        }

        try {
            // Ruta donde se guardará el archivo (ejemplo: uploads/)
            String directorio = "uploads/";
            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path rutaArchivo = Paths.get(directorio, nombreArchivo);

            // Crear el directorio si no existe
            Files.createDirectories(rutaArchivo.getParent());
            Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            // Crear entidad Archivo
            Archivo archivoEntidad = new Archivo();
            archivoEntidad.setTarea(tarea);
            archivoEntidad.setArchivoUrl(rutaArchivo.toString());

            archivoRepository.save(archivoEntidad);

            return new ArchivoDTO(archivoEntidad);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }


    @Override
    public List<ArchivoDTO> obtenerArchivosDeTarea(Tarea tarea) {
         tarea = tareaRepository.findById(tarea.getTareaId())
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));

        List<Archivo> archivos = archivoRepository.findByTarea(tarea);

        return archivos.stream()
                .map(ArchivoDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public void eliminarArchivo(Integer archivoId) {
        Archivo archivo = archivoRepository.findById(archivoId)
                .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado con ID: " + archivoId));

        // Eliminar archivo físico si existe
        try {
            Path rutaArchivo = Paths.get(archivo.getArchivoUrl());
            Files.deleteIfExists(rutaArchivo);
        } catch (IOException e) {
            // Loguear, pero continuar con la eliminación en BD
        }

        archivoRepository.delete(archivo);
    }

}


