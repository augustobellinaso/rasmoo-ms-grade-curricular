package com.rasmoo.cliente.escola.gradecurricular.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/materia")
public class MateriaController {

    @GetMapping(path = "/")
    public ResponseEntity<String> helloWorldRest() {
        return ResponseEntity.status(HttpStatus.OK).body("Ola Mundo REST!");
    }

}
