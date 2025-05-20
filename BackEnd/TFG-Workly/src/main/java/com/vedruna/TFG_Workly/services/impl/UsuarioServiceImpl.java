package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.AsignacionDTO;
import com.vedruna.TFG_Workly.dto.ProyectoDTO;
import com.vedruna.TFG_Workly.dto.TareaDTO;
import com.vedruna.TFG_Workly.dto.UsuarioDTO;
import com.vedruna.TFG_Workly.models.Asignacion;
import com.vedruna.TFG_Workly.models.Proyecto;
import com.vedruna.TFG_Workly.models.Tarea;
import com.vedruna.TFG_Workly.models.Usuario;
import com.vedruna.TFG_Workly.repositories.IAsignacionRepository;
import com.vedruna.TFG_Workly.repositories.IProyectoRepository;
import com.vedruna.TFG_Workly.repositories.ITareaRepository;
import com.vedruna.TFG_Workly.repositories.IUsuarioRepository;
import com.vedruna.TFG_Workly.services.UsuarioServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioServiceI {
    @Autowired
    IUsuarioRepository userRepository;

    @Autowired
    IProyectoRepository proyectoRepository;

    @Autowired
    ITareaRepository tareaRepository;

    @Autowired
    IAsignacionRepository asignacionRepository;



    @Override
    public UsuarioDTO obtenerPerfil() {
        // Obtener usuario autenticado del contexto de seguridad
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        String email = auth.getName(); // ahora sí es el email real
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UsuarioDTO(user);
    }




    @Override
    public UsuarioDTO obtenerUsuarioPorId(Integer userid) {
        Usuario user = userRepository.findById(userid).orElseThrow(() -> new EmptyResultDataAccessException("Usuario no encontrado", 1));
        return new UsuarioDTO(user);

    }

    @Override
    public List<UsuarioDTO> obtenerUsuarios() {
        List<Usuario> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public List<UsuarioDTO> buscarPorEmail(String email) {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmptyResultDataAccessException("Usuario no encontrado", 1));

        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        usuariosDTO.add(new UsuarioDTO(user));

        return usuariosDTO;
    }


    @Override
    public UsuarioDTO actualizarPerfil(UsuarioDTO usuarioDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setFoto(usuarioDTO.getFoto());
        Usuario actualizado = userRepository.save(usuario);
        return new UsuarioDTO(actualizado);
    }


    @Override
    public List<ProyectoDTO> obtenerProyectosDelUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Proyecto> proyectos = proyectoRepository.findByUsuario_UsuarioId(usuario.getUsuarioId());

        return proyectos.stream()
                .map(ProyectoDTO::new)
                .collect(Collectors.toList());
    }


    @Override
    public List<AsignacionDTO> obtenerTareasAsignadas() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Asignacion> tareasAsignadas = asignacionRepository.findByUsuario_UsuarioId(usuario.getUsuarioId());

        return tareasAsignadas.stream()
                .map(AsignacionDTO::new)
                .collect(Collectors.toList());
    }



}