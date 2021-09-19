package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@RestController
@RequestMapping(path = "/materia")
public class MateriaController {

    @Autowired
    private IMateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> listaMaterias() {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.listarTodas());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<MateriaDTO>> consultarMateria(@PathVariable Long id) {
        Response<MateriaDTO> response = new Response<>();
        response.setData(this.materiaService.consultar(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .consultarMateria(id))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Boolean> cadastrarMateria(@Valid @RequestBody MateriaDTO materia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.materiaService.cadastrar(materia));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.excluir(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@Valid @RequestBody MateriaDTO materia) {
        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.atualizar(materia));
    }

}
