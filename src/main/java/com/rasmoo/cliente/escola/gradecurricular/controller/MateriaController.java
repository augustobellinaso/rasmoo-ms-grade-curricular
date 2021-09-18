package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@RestController
@RequestMapping(path = "/materia")
public class MateriaController {

    @Autowired
    private IMateriaService materiaService;

    @Autowired
    private IMateriaRepository materiaRepository;

    @GetMapping
    public ResponseEntity<List<MateriaEntity>> listaMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaRepository.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MateriaEntity> consultaMateria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaRepository.findById(id).get());
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastrarMateria(@RequestBody MateriaEntity materia) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.cadastrar(materia));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.excluir(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@RequestBody MateriaEntity materia) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.atualizar(materia));
    }

}
