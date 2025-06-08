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

    //Implementacion de la creacion del proyecto
    @Override
    public ProyectoDTO crearProyecto(CrearProyectoDTO dto, Usuario usuario) {
        //Obtenemos el usuario ya autenticado
       usuario = getUsuarioAutenticado();
        //Creamos y seteamos los valores del proyecto que haya puesto el usuario
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(dto.getNombre());
        proyecto.setVisibilidad(dto.isVisibilidad());
        proyecto.setUsuario(usuario);

        //Guardamos y devolvemos el proyecto
        Proyecto proyectoGuardado = proyectoRepository.save(proyecto);

        return new ProyectoDTO(proyectoGuardado);
    }

    //Metodo para obtener el usuario autenticado
    public Usuario getUsuarioAutenticado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }




    //Implementacion de obtener el proyeto por id
    @Override
    public ProyectoDTO obtenerProyectoPorId(Integer id) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con ID: " + id));
        return new ProyectoDTO(proyecto);
    }

    //Implementacion para obtener los proyectos compartidos contigo
    @Override
    public List<ProyectoDTO> obtenerProyectosComoColaborador(Integer usuarioId) {
        List<Colaborador> colaboraciones = colaboradorRepository.findByUsuario_UsuarioId(usuarioId);
        return colaboraciones.stream()
                .map(c -> new ProyectoDTO(c.getProyecto()))
                .collect(Collectors.toList());
    }

    //Implementacion para actualizar el proyecto
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

    //Implementacion para eliminar el proyecto
    @Override
    public void eliminarProyecto(Integer id) {
        if (!proyectoRepository.existsById(id)) {
            throw new EntityNotFoundException("Proyecto no encontrado");
        }
        proyectoRepository.deleteById(id);
    }

    //Implementacion para mostrar los proyectos del usuario
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


    //Implementacion para añadir un colaborador
    @Override
    public void añadirColaborador(Integer proyectoId, Integer usuarioId, String rol) {
        //Obtenemos proyecto y usuario
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        //Verificamos si ya existe el colaborador
        boolean yaEsColaborador = colaboradorRepository.existsByProyecto_ProyectoIdAndProyecto_Usuario_UsuarioId(proyectoId, usuarioId);
        if (yaEsColaborador) {
            throw new IllegalArgumentException("El usuario ya es colaborador de este proyecto");
        }
        //Creamos y guardamos el colaborador
        Colaborador nuevoColaborador = new Colaborador();
        nuevoColaborador.setProyecto(proyecto);
        nuevoColaborador.setUsuario(usuario);
        nuevoColaborador.setRol(rol);

        colaboradorRepository.save(nuevoColaborador);
    }

    //Implementacion para eliminar el colaborador
    @Override
    public void eliminarColaborador(Integer proyectoId, Integer usuarioId) {
        Colaborador colaborador = colaboradorRepository.findByProyecto_ProyectoIdAndUsuario_UsuarioId(proyectoId, usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Colaborador no encontrado"));

        colaboradorRepository.delete(colaborador);
    }

    //Implementacion para mostrar los colaboradores que hay en el proyecto
    @Override
    public List<UsuarioDTO> listarColaboradores(Integer proyectoId) {
        List<Colaborador> colaboradores = colaboradorRepository.findByProyecto_ProyectoId(proyectoId);

        return colaboradores.stream()
                .map(colab -> new UsuarioDTO(colab.getUsuario()))
                .collect(Collectors.toList());
    }





}


