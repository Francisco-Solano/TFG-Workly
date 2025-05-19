package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ComentarioDTO;
import com.vedruna.TFG_Workly.models.Comentario;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IComentarioRepository;
import com.vedruna.TFG_Workly.repositories.ITareaRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.ComentarioServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioServiceI {
    @Autowired
    private IComentarioRepository comentarioRepository;

    @Autowired
    private ITareaRepository tareaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public ComentarioDTO crearComentario(Integer tareaId, ComentarioDTO comentarioDTO) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));

        Usuario usuario = usuarioRepository.findById(comentarioDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Comentario comentario = new Comentario();
        comentario.setTarea(tarea);
        comentario.setUsuario(usuario);
        comentario.setContenido(comentarioDTO.getContenido());
        comentario.setFechaCreacion(new Date()); // o LocalDateTime.now()

        Comentario guardado = comentarioRepository.save(comentario);
        return new ComentarioDTO(guardado);
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Integer comentarioId) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado"));
        return new ComentarioDTO(comentario);
    }

    @Override
    public List<ComentarioDTO> listarComentariosPorTarea(Integer tareaId) {
        List<Comentario> comentarios = comentarioRepository.findByTarea_TareaId(tareaId);
        return comentarios.stream().map(ComentarioDTO::new).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO actualizarComentario(Integer comentarioId, ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado"));

        if (comentarioDTO.getContenido() != null) {
            comentario.setContenido(comentarioDTO.getContenido());
        }

        Comentario actualizado = comentarioRepository.save(comentario);
        return new ComentarioDTO(actualizado);
    }

    @Override
    public void eliminarComentario(Integer comentarioId) {
        if (!comentarioRepository.existsById(comentarioId)) {
            throw new EntityNotFoundException("Comentario no encontrado");
        }
        comentarioRepository.deleteById(comentarioId);
    }
}


