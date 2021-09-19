package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rasmoo.cliente.escola.gradecurricular.constante.HyperLinkConstant;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

@RestController
@RequestMapping(path = "/materia")
public class MateriaController {

    @Autowired
    private IMateriaService materiaService;

    @GetMapping
    public ResponseEntity<Response<List<MateriaDTO>>> listarMaterias() {
        Response<List<MateriaDTO>> response = new Response<>();
        response.setData(this.materiaService.listarTodas());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .listarMaterias())
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<MateriaDTO>> consultarMateria(@PathVariable Long id) {
        Response<MateriaDTO> response = new Response<>();
        MateriaDTO materia = this.materiaService.consultar(id);
        response.setData(materia);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .consultarMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .excluirMateria(id))
                .withRel(HyperLinkConstant.EXCLUIR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .atualizarMateria(materia))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaDTO materia) {
        Response<Boolean> response = new Response<>();
        response.setData(this.materiaService.cadastrar(materia));
        response.setStatusCode(HttpStatus.CREATED.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).cadastrarMateria(materia))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia))
                .withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id) {
        Response<Boolean> response = new Response<>();
        response.setData(this.materiaService.excluir(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).excluirMateria(id))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaDTO materia) {
        Response<Boolean> response = new Response<>();
        response.setData(this.materiaService.atualizar(materia));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/horario-minimo/{horaMinima}")
    public ResponseEntity<Response<List<MateriaDTO>>> consultarMateriaPorHoraMinima(@PathVariable int horaMinima) {
        Response<List<MateriaDTO>> response = new Response<>();
        List<MateriaDTO> materia = this.materiaService.listarPorHoraMinima(horaMinima);
        response.setData(materia);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .consultarMateriaPorHoraMinima(horaMinima))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/frequencia/{freq}")
    public ResponseEntity<Response<List<MateriaDTO>>> consultarMateriaPorFrequencia(@PathVariable int freq) {
        Response<List<MateriaDTO>> response = new Response<>();
        List<MateriaDTO> materia = this.materiaService.listarPorFrequencia(freq);
        response.setData(materia);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .consultarMateriaPorFrequencia(freq))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
