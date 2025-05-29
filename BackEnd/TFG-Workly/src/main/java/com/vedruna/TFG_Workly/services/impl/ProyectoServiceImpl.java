package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.CrearProyectoDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ProyectoDTO crearProyecto(CrearProyectoDTO dto, Usuario usuario) {
       usuario = getUsuarioAutenticado(); // o con el código que mencionaste

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.getNombre());
        proyecto.setVisibilidad(dto.isVisibilidad());
        proyecto.setUsuario(usuario);

        Proyecto proyectoGuardado = proyectoRepository.save(proyecto);

        return new ProyectoDTO(proyectoGuardado);
    }
    public Usuario getUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }





    @Override
    public ProyectoDTO obtenerProyectoPorId(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + id));
        return new ProyectoDTO(proyecto);
    }

    @Override
    public List<ProyectoDTO> obtenerProyectosComoColaborador(Integer usuarioId) {
        List<Colaborador> colaboraciones = colaboradorRepository.findByUsuario_UsuarioId(usuarioId);
        return colaboraciones.stream()
                .map(c -> new ProyectoDTO(c.getProyecto()))
                .collect(Collectors.toList());
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
        Colaborador colaborador = colaboradorRepository.findByProyecto_ProyectoIdAndUsuario_UsuarioId(proyectoId, usuarioId)
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
    public List<ProyectoDTO> buscarProyectosPublicos() {
        List<Proyecto> proyectos = proyectoRepository.findByVisibilidad(true)
                .stream().collect(Collectors.toList());
        return proyectos.stream().map(ProyectoDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ProyectoDTO> buscarProyectosPrivados() {
        List<Proyecto> proyectos = proyectoRepository.findByVisibilidad(false)
                .stream().collect(Collectors.toList());
        return proyectos.stream().map(ProyectoDTO::new).collect(Collectors.toList());
    }

    @Override
    public void asignarRol(Integer proyectoId, Integer usuarioId, String nuevoRol) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + proyectoId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        // Verifica si el usuario es colaborador del proyecto
        Colaborador colaboracion = proyecto.getColaboradores().stream()
                .filter(c -> c.getUsuario().getUsuarioId().equals(usuarioId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El usuario no es colaborador del proyecto"));

        // Validar el rol (puedes usar Enum o una lista definida)
        List<String> rolesValidos = List.of("ROLE_ADMIN", "ROLE_USER");
        if (!rolesValidos.contains(nuevoRol.toUpperCase())) {
            throw new IllegalArgumentException("Rol no válido: " + nuevoRol);
        }

        // Asignar el nuevo rol
        colaboracion.setRol(nuevoRol.toUpperCase());

        // Guardar cambios en el proyecto
        proyectoRepository.save(proyecto);
    }



}


