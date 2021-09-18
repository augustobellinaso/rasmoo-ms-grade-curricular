package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@RestController
@RequestMapping(path = "/materia")
public class MateriaController {

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
        try {
            this.materiaRepository.save(materia);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
        try {
            this.materiaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@RequestBody MateriaEntity materia) {
        try {
            MateriaEntity materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();
            materiaAtualizada.setNome(materia.getNome());
            materiaAtualizada.setCodigo(materia.getCodigo());
            materiaAtualizada.setFrequencia(materia.getFrequencia());
            materiaAtualizada.setHoras(materia.getHoras());

            this.materiaRepository.save(materia);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(false);
        }
    }

}
