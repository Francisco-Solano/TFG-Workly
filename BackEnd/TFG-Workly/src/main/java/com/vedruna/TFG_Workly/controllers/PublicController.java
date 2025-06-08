package com.vedruna.TFG_Workly.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    //Controlador para que cuando se depliegue el backend, el enlace lleve a algo para asegurarnos de que funciona.
    @GetMapping("/")
    public String index() {
        return "Backend desplegado correctamente 🚀";
    }
}
