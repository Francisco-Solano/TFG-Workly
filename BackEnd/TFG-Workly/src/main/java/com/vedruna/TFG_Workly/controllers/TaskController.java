package com.vedruna.TFG_Workly.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @GetMapping
    public String getTasks() {
        return "Lista de tareas";
    }
}
