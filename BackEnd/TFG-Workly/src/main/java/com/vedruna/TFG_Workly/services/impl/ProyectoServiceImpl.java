package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
//import com.vedruna.TFG_Workly.models.Colaborador;
import com.vedruna.TFG_Workly.models.Colaborador;
import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Usuario;
//import com.vedruna.TFG_Workly.repositories.IColaboradorRepository;
import com.vedruna.TFG_Workly.repositories.IColaboradorRepository;
import com.vedruna.TFG_Workly.repositories.IProyectoRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.ProyectoServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectoServiceImpl implements ProyectoServiceI {
    @Autowired
    IProyectoRepository proyectoRepository;
    @Autowired
     IUsuarioRepository usuarioRepository;

    @Autowired
    IColaboradorRepository colaboradorRepository;



    @Autowired
    public ProyectoServiceImpl(IProyectoRepository proyectoRepository, IUsuarioRepository usuarioRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ProyectoDTO crearProyecto(ProyectoDTO proyectoDTO) {
        System.out.println("adminId recibido: " + proyectoDTO.getAdminId());

        Usuario admin = usuarioRepository.findById(proyectoDTO.getAdminId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario administrador no encontrado"));

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setVisibilidad(proyectoDTO.isVisibilidad());
        proyecto.setUsuario(admin);


        Proyecto guardado = proyectoRepository.save(proyecto);
        return new ProyectoDTO(guardado);
    }

    @Override
    public ProyectoDTO obtenerProyectoPorId(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + id));
        return new ProyectoDTO(proyecto);
    }

    @Override
    public ProyectoDTO actualizarProyecto(Integer id, ProyectoDTO proyectoDTO) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        if (proyectoDTO.getNombre() != null) {
            proyecto.setNombre(proyectoDTO.getNombre());
        }
        proyecto.setVisibilidad(proyectoDTO.isVisibilidad());

        Proyecto actualizado = proyectoRepository.save(proyecto);
        return new ProyectoDTO(actualizado);
    }

    @Override
    public void eliminarProyecto(Integer id) {
        if (!proyectoRepository.existsById(id)) {
            throw new EntityNotFoundException("Proyecto no encontrado");
        }
        proyectoRepository.deleteById(id);
    }

    @Override
    public List<ProyectoDTO> listarProyectosUsuario() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Proyecto> proyectos = proyectoRepository.findByUsuario_UsuarioId(usuario.getUsuarioId());

        return proyectos.stream()
                .map(ProyectoDTO::new)
                .collect(Collectors.toList());
    }



    @Override
    public void añadirColaborador(Integer proyectoId, Integer usuarioId, String rol) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        boolean yaEsColaborador = colaboradorRepository.existsByProyecto_ProyectoIdAndProyecto_Usuario_UsuarioId(proyectoId, usuarioId);
        if (yaEsColaborador) {
            throw new IllegalArgumentException("El usuario ya es colaborador de este proyecto");
        }

        Colaborador nuevoColaborador = new Colaborador();
        nuevoColaborador.setProyecto(proyecto);
        nuevoColaborador.setUsuario(usuario);
        nuevoColaborador.setRol(rol);

        colaboradorRepository.save(nuevoColaborador);
    }


    @Override
    public void eliminarColaborador(Integer proyectoId, Integer usuarioId) {
        Colaborador colaborador = colaboradorRepository.findByProyecto_ProyectoIdAndProyecto_Usuario_UsuarioId(proyectoId, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Colaborador no encontrado"));

        colaboradorRepository.delete(colaborador);
    }


    @Override
    public List<UsuarioDTO> listarColaboradores(Integer proyectoId) {
        List<Colaborador> colaboradores = colaboradorRepository.findByProyecto_ProyectoId(proyectoId);

        return colaboradores.stream()
                .map(colab -> new UsuarioDTO(colab.getUsuario()))
                .collect(Collectors.toList());
    }


    @Override
    public ProyectoDTO cambiarVisibilidad(Integer proyectoId, boolean nuevaVisibilidad) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));
        proyecto.setVisibilidad(nuevaVisibilidad);
        Proyecto actualizado = proyectoRepository.save(proyecto);
        return new ProyectoDTO(actualizado);
    }

    @Override
    public List<ProyectoDTO> buscarProyectosPublicos(String nombre) {
        List<Proyecto> proyectos = proyectoRepository.findByVisibilidad(true)
                .stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());

        return proyectos.stream().map(ProyectoDTO::new).collect(Collectors.toList());
    }
}


