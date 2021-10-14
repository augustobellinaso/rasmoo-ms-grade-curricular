package com.rasmoo.cliente.escola.gradecurricular.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class CursoControllerIntegratedTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ICursoRepository cursoRepository;

    @Autowired
    private IMateriaRepository materiaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setup() throws Exception {
        this.montarBaseDeDados();
        this.montaMateriaBaseDeDados();
    }

    @AfterEach
    public void finish() {
        this.cursoRepository.deleteAll();
        this.materiaRepository.deleteAll();
    }

    private void montaMateriaBaseDeDados() {
        MateriaEntity m1 = new MateriaEntity();
        m1.setCodigo("ILP");
        m1.setFrequencia(2);
        m1.setHoras(64);
        m1.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        MateriaEntity m2 = new MateriaEntity();
        m2.setCodigo("POO");
        m2.setFrequencia(2);
        m2.setHoras(84);
        m2.setNome("PROGRAMACAO ORIENTADA A OBJETOS");

        MateriaEntity m3 = new MateriaEntity();
        m3.setCodigo("APA");
        m3.setFrequencia(1);
        m3.setHoras(102);
        m3.setNome("ANALISE E PROJETOS DE ALGORITMOS");

        this.materiaRepository.saveAll(Arrays.asList(m1, m2, m3));
    }

    private void montarBaseDeDados() {
        CursoEntity c1 = new CursoEntity();
        c1.setNome("Curso 1");
        c1.setCodigo("C1");
        c1.setMaterias(new ArrayList<>());

        CursoEntity c2 = new CursoEntity();
        c2.setNome("Curso 2");
        c2.setCodigo("C2");
        c2.setMaterias(new ArrayList<>());

        CursoEntity c3 = new CursoEntity();
        c3.setNome("Curso 3");
        c3.setCodigo("C3");
        c3.setMaterias(new ArrayList<>());
        
        this.cursoRepository.saveAll(Arrays.asList(c1, c2, c3));
    }

    @Test
    void testListarCursos() {
        ResponseEntity<Response<List<CursoModel>>> cursos = restTemplate
                .exchange("http://localhost:" + this.port + "/curso", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        assertNotNull(cursos.getBody().getData());
        assertEquals(3, cursos.getBody().getData().size());
        assertEquals(200, cursos.getBody().getStatusCode());
    }

    @Test
    void testConsultarCursoPorCodigo() {
        ResponseEntity<Response<CursoEntity>> cursos = restTemplate
                .exchange("http://localhost:" + this.port + "/curso/C1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        assertNotNull(cursos.getBody().getData());
        assertEquals("Curso 1", cursos.getBody().getData().getNome());
        assertEquals(200, cursos.getBody().getStatusCode());
    }

    @Test
    void testAtualizarCurso() {
        List<CursoEntity> cursosList = this.cursoRepository.findAll();
        CursoEntity cursoEntity = cursosList.get(0);

        CursoModel cursoModel = new CursoModel();
        cursoModel.setId(cursoEntity.getId());
        cursoModel.setCodCurso(cursoEntity.getCodigo());
        cursoModel.setNome("Curso 1 atualizado");

        HttpEntity<CursoModel> request = new HttpEntity<>(cursoModel);
        ResponseEntity<Response<Boolean>> cursos = restTemplate
                .exchange("http://localhost:" + this.port + "/curso", HttpMethod.PUT, request,
                        new ParameterizedTypeReference<>() {
                        });

        CursoEntity atualizado = this.cursoRepository.findCursoByCodigo("C1");
        assertTrue(cursos.getBody().getData());
        assertEquals("Curso 1 atualizadp", atualizado.getNome());
        assertEquals(200, cursos.getBody().getStatusCode());
    }

    @Test
    void testCadastrarCurso() {
        List<MateriaEntity> materias = this.materiaRepository.findAll();
        List<Long> idMaterias = new ArrayList<>();
        idMaterias.add(materias.get(0).getId());
        idMaterias.add(materias.get(1).getId());


        CursoModel cursoModel = new CursoModel();
        cursoModel.setNome("Curso 4");
        cursoModel.setCodCurso("C4");
        cursoModel.setMaterias(idMaterias);

        HttpEntity<CursoModel> request = new HttpEntity<>(cursoModel);

        ResponseEntity<Response<Boolean>> cursos = restTemplate
                .exchange("http://localhost:" + this.port + "/curso", HttpMethod.POST, request,
                        new ParameterizedTypeReference<>() {
                        });

        List<CursoEntity> atualizados = this.cursoRepository.findAll();
        assertTrue(cursos.getBody().getData());
        assertEquals(4, atualizados.size());
        assertEquals(200, cursos.getBody().getStatusCode());
    }

    @Test
    void testExcluirCursoPorId() {
        List<CursoEntity> materiaList = this.cursoRepository.findAll();
        Long id = materiaList.get(0).getId();

        ResponseEntity<Response<Boolean>> curso = restTemplate.exchange("http://localhost:" + this.port + "/curso/1", HttpMethod.DELETE,
                null, new ParameterizedTypeReference<Response<Boolean>>() {
                });

        List<CursoEntity> listCursoAtualizado = this.cursoRepository.findAll();

        assertTrue(curso.getBody().getData());
        assertEquals(0, listCursoAtualizado.size());
        assertEquals(200, curso.getBody().getStatusCode());
    }
}
