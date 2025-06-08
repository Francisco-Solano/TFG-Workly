package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ColaboradorDTO;
import com.vedruna.TFG_Workly.models.Colaborador;
import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IColaboradorRepository;
import com.vedruna.TFG_Workly.repositories.IProyectoRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.ColaboradorServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColaboradorServiceImpl implements ColaboradorServiceI {
    @Autowired
    private IColaboradorRepository colaboradorRepository;

    @Autowired
    private IProyectoRepository proyectoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;


    //Implementacion del metodo para añadir un colaborador al proyecto
    @Override
    public ColaboradorDTO añadirColaborador(Integer proyectoId, Integer usuarioId, String rol) {
        //Obtenemos el proyecto y el usuario
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Verifica si ya existe ese colaborador
        Optional<Colaborador> existente = colaboradorRepository.findByProyecto_ProyectoIdAndUsuario_UsuarioId(proyectoId, usuarioId);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El usuario ya es colaborador de este proyecto");
        }
        //Crea y añade los datos del colaborador
        Colaborador colaborador = new Colaborador();
        colaborador.setProyecto(proyecto);
        colaborador.setUsuario(usuario);
        colaborador.setRol(rol);

        //Guarda y devuelve el colaborador
        Colaborador guardado = colaboradorRepository.save(colaborador);
        return new ColaboradorDTO(guardado);
    }


    @Override
    public List<ColaboradorDTO> obtenerColaboradoresDeProyecto(Integer proyectoId) {
        List<Colaborador> colaboradores = colaboradorRepository.findByProyecto_ProyectoId(proyectoId);
        return colaboradores.stream()
                .map(ColaboradorDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ColaboradorDTO actualizarRolColaborador(Integer colaboradorId, String nuevoRol) {
        Colaborador colaborador = colaboradorRepository.findById(colaboradorId)
                .orElseThrow(() -> new EntityNotFoundException("Colaborador no encontrado"));
        colaborador.setRol(nuevoRol);
        return new ColaboradorDTO(colaboradorRepository.save(colaborador));
    }

    @Override
    public void eliminarColaborador(Integer colaboradorId) {
        if (!colaboradorRepository.existsById(colaboradorId)) {
            throw new EntityNotFoundException("Colaborador no encontrado");
        }
        colaboradorRepository.deleteById(colaboradorId);
    }

    //Metodo que verifica si el colaborador ya existe
    @Override
    public boolean esColaborador(Integer proyectoId, Integer usuarioId) {
        return colaboradorRepository.existsByProyecto_ProyectoIdAndProyecto_Usuario_UsuarioId(proyectoId, usuarioId);
    }
}

