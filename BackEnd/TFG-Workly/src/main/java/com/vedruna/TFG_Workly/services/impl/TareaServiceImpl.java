package com.vedruna.TFG_Workly.services.impl;

//import com.vedruna.TFG_Workly.dto.CrearTareaDTO;
import com.vedruna.TFG_Workly.dto.CrearTareaDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.*;
import com.vedruna.TFG_Workly.repositories.*;
import com.vedruna.TFG_Workly.services.TareaServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class TareaServiceImpl implements TareaServiceI {
    @Autowired
    ITablaRepository tablaRepository;

    @Autowired
    ITareaRepository tareaRepository;
/*
    @Autowired
    IEtiquetaRepository etiquetaRepository;
*/


    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    private IAsignacionRepository asignacionRepository;




    @Override
    public TareaDTO crearTarea(CrearTareaDTO crearTareaDTO) {
        // Validar título obligatorio
        if (crearTareaDTO.getTitulo() == null || crearTareaDTO.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título de la tarea es obligatorio.");
        }

        // Buscar la tabla
        Tabla tabla = tablaRepository.findById(crearTareaDTO.getTablaId())
                .orElseThrow(() -> new RuntimeException("Tabla no encontrada con ID: " + crearTareaDTO.getTablaId()));

        // Crear la tarea
        Tarea tarea = new Tarea();
        tarea.setTitulo(crearTareaDTO.getTitulo());
        tarea.setDescripcion(crearTareaDTO.getDescripcion());
        tarea.setEstado(crearTareaDTO.getEstado());
        tarea.setPrioridad(crearTareaDTO.getPrioridad());
        tarea.setFechaLimite(crearTareaDTO.getFechaLimite());
        tarea.setTabla(tabla);
/*
        // Asignar etiquetas si las hay
        if (crearTareaDTO.getEtiquetaIds() != null && !crearTareaDTO.getEtiquetaIds().isEmpty()) {
            List<Etiqueta> etiquetas = etiquetaRepository.findAllById(crearTareaDTO.getEtiquetaIds());
            tarea.setEtiquetas((Set<Etiqueta>) etiquetas);
        }
*/
        // Guardar tarea
        Tarea tareaGuardada = tareaRepository.save(tarea);

        // Devolver DTO
        return new TareaDTO(tareaGuardada);
    }





    @Override
    public TareaDTO obtenerTareaPorId(Integer tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId).orElseThrow(() -> new EntityNotFoundException("tarea no encontrada"));
        return new TareaDTO(tarea);
    }

    @Override
    public TareaDTO actualizarTarea(Integer tareaId, CrearTareaDTO tareaDTO) {
        // Buscar la tarea existente
        Tarea tareaExistente = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con ID: " + tareaId));

        // Actualizar campos si se proporcionan
        if (tareaDTO.getTitulo() != null && !tareaDTO.getTitulo().isBlank()) {
            tareaExistente.setTitulo(tareaDTO.getTitulo());
        }

        if (tareaDTO.getDescripcion() != null) {
            tareaExistente.setDescripcion(tareaDTO.getDescripcion());
        }

        if (tareaDTO.getEstado() != null) {
            tareaExistente.setEstado(tareaDTO.getEstado());
        }

        if (tareaDTO.getPrioridad() != null) {
            tareaExistente.setPrioridad(tareaDTO.getPrioridad());
        }

        if (tareaDTO.getFechaLimite() != null) {
            tareaExistente.setFechaLimite(tareaDTO.getFechaLimite());
        }

        if (tareaDTO.getTablaId() != null) {
            Tabla nuevaTabla = tablaRepository.findById(tareaDTO.getTablaId())
                    .orElseThrow(() -> new RuntimeException("Tabla no encontrada con ID: " + tareaDTO.getTablaId()));
            tareaExistente.setTabla(nuevaTabla);
        }
/*
        if (tareaDTO.getEtiquetaIds() != null) {
            List<Etiqueta> etiquetas = etiquetaRepository.findAllById(tareaDTO.getEtiquetaIds());
            tareaExistente.setEtiquetas((Set<Etiqueta>) etiquetas);
        }

 */

        // Guardar cambios
        tareaRepository.save(tareaExistente);

        return new TareaDTO(tareaExistente);
    }




    @Override
    public void eliminarTarea(Integer tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        tareaRepository.delete(tarea);
    }

    @Override
    public void moverTarea(Integer tareaId, Integer nuevaTablaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        Tabla nuevaTabla = tablaRepository.findById(nuevaTablaId)
                .orElseThrow(() -> new EntityNotFoundException("Tabla no encontrada con ID: " + nuevaTablaId));

        tarea.setTabla(nuevaTabla);
        tareaRepository.save(tarea);
    }

    @Override
    public void actualizarPosicionTarea(Integer tareaId, Integer nuevaPosicion) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));
        tarea.setPosicion(nuevaPosicion);
        tareaRepository.save(tarea);
    }



    @Override
    public void cambiarEstado(Integer tareaId, String nuevoEstado) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));
        tarea.setEstado(nuevoEstado);
        tareaRepository.save(tarea);

    }

    @Override
    public void cambiarPrioridad(Integer tareaId, String nuevaPrioridad) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));
        tarea.setPrioridad(nuevaPrioridad);
        tareaRepository.save(tarea);

    }
/*
    @Override
    @Transactional
    public void añadirEtiqueta(Integer tareaId, Integer etiquetaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new EntityNotFoundException("Etiqueta no encontrada con ID: " + etiquetaId));

        // Inicializar lista si es null
        if (tarea.getEtiquetas() == null) {
            tarea.setEtiquetas((Set<Etiqueta>) etiqueta);
        }

        // Evitar etiquetas duplicadas
        if (!tarea.getEtiquetas().contains(etiqueta)) {
            tarea.getEtiquetas().add(etiqueta);
            tareaRepository.save(tarea);
        }
    }


    @Override
    public void quitarEtiqueta(Integer tareaId, Integer etiquetaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        Etiqueta etiqueta = etiquetaRepository.findById(etiquetaId)
                .orElseThrow(() -> new EntityNotFoundException("Etiqueta no encontrada con ID: " + etiquetaId));

        if (tarea.getEtiquetas().contains(etiqueta)) {
            tarea.getEtiquetas().remove(etiqueta);
            tareaRepository.save(tarea);
        }   }

*/


    @Override
    public void asignarUsuario(Integer tareaId, Integer usuarioId) {
        boolean yaAsignado = asignacionRepository.existsByTarea_TareaIdAndUsuario_UsuarioId(tareaId, usuarioId);
        if (yaAsignado) {
            return; // O lanzar una excepción si lo prefieres
        }

        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        Asignacion asignacion = new Asignacion();
        asignacion.setTarea(tarea);
        asignacion.setUsuario(usuario);

        asignacionRepository.save(asignacion);
    }


    @Override
    public void desasignarUsuario(Integer tareaId, Integer usuarioId) {
        List<Asignacion> asignaciones = asignacionRepository.findByTarea_TareaId(tareaId);
        for (Asignacion asignacion : asignaciones) {
            if (asignacion.getUsuario().getUsuarioId().equals(usuarioId)) {
                asignacionRepository.delete(asignacion);
                break;
            }
        }
    }




    @Override
    public void actualizarFechaLimite(Integer tareaId, Date nuevaFechaLimite) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada con ID: " + tareaId));
        tarea.setFechaLimite(nuevaFechaLimite);
        tareaRepository.save(tarea);
    }

    @Override
    public List<TareaDTO> listarTareasPorTabla(Integer tablaId) {
        Tabla tabla = tablaRepository.findById(tablaId)
                .orElseThrow(() -> new EntityNotFoundException("Tabla no encontrada con ID: " + tablaId));

        List<Tarea> tareas = tareaRepository.findByTabla_TablaId(tablaId);
        return tareas.stream().map(TareaDTO::new).toList();
    }


    @Override
    public List<TareaDTO> listarTareasAsignadasAlUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        List<Asignacion> asignaciones = asignacionRepository.findByUsuario_UsuarioId(usuario.getUsuarioId());

        List<TareaDTO> tareasDTO = new ArrayList<>();
        for (Asignacion asignacion : asignaciones) {
            tareasDTO.add(new TareaDTO(asignacion.getTarea()));
        }

        return tareasDTO;
    }



}


