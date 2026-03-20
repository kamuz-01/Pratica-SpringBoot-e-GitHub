package org.SpringBoot_GitHub.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1")
public class UsuarioController {

    @GetMapping("/saludo")
    public String olá() {
        return "Olá, Spring Boot!";
    }
}