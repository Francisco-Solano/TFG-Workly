package com.vedruna.TFG_Workly.services.impl;

import com.vedruna.TFG_Workly.dto.ArchivoDTO;
import com.vedruna.TFG_Workly.services.ArchivoServiceI;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ArchivoServiceImpl implements ArchivoServiceI {


    @Override
    public ArchivoDTO subirArchivo(Integer tareaId, MultipartFile archivo) {
        return null;
    }

    @Override
    public List<ArchivoDTO> obtenerArchivosDeTarea(Integer tareaId) {
        return null;
    }

    @Override
    public void eliminarArchivo(Integer archivoId) {

    }
}
