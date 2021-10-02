package com.rasmoo.cliente.escola.gradecurricular.controller;

import static org.junit.jupiter.api.Assertions.*;

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
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerIntegratedTest {

    @LocalServerPort
    private int port;

    @Autowired
    private IMateriaRepository materiaRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void init() {
        this.montaBaseDeDados();
    }
    @AfterEach
    public void finish() {
        this.materiaRepository.deleteAll();
    }

    private void montaBaseDeDados() {
        MateriaEntity m1 = new MateriaEntity();
        m1.setNome("Materia 1");
        m1.setCodigo("ILP");
        m1.setHoras(34);
        m1.setFrequencia(1);

        MateriaEntity m2 = new MateriaEntity();
        m2.setNome("Materia 2");
        m2.setCodigo("ILP2");
        m2.setHoras(68);
        m2.setFrequencia(2);

        MateriaEntity m3 = new MateriaEntity();
        m3.setNome("Materia 3");
        m3.setCodigo("ILP3");
        m3.setHoras(34);
        m3.setFrequencia(1);

        this.materiaRepository.saveAll(Arrays.asList(m1, m2, m3));
    }


    @Test
    void testListarMaterias() {
        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(3, materias.getBody().getData().size());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testConsultarMateriaPorHoraMinima64() {

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia/horario-minimo/64", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(1, materias.getBody().getData().size());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testConsultarMateriaPorFrequencia1() {

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia/frequencia/1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(2, materias.getBody().getData().size());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testConsultarMateriaPorId() {
        List<MateriaEntity> materiasList = this.materiaRepository.findAll();
        Long id = materiasList.get(0).getId();

        ResponseEntity<Response<MateriaDTO>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<MateriaDTO>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(id, materias.getBody().getData().getId());
        assertEquals(34, materias.getBody().getData().getHoras());
        assertEquals("ILP", materias.getBody().getData().getCodigo());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testAtualizarMateria() {
        List<MateriaEntity> materiasList = this.materiaRepository.findAll();
        MateriaEntity materia = materiasList.get(0);

        materia.setNome("Teste Atualiza Materia");

        HttpEntity<MateriaEntity> request = new HttpEntity<>(materia);

        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.PUT, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });

        MateriaEntity materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();
        assertTrue(materias.getBody().getData());
        assertEquals("Teste Atualiza Materia", materiaAtualizada.getNome());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testCadastrarMateria() {
        MateriaEntity m4 = new MateriaEntity();
        m4.setNome("Materia 4");
        m4.setCodigo("ILP4");
        m4.setHoras(102);
        m4.setFrequencia(2);

        HttpEntity<MateriaEntity> request = new HttpEntity<>(m4);

        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.POST, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });

        List<MateriaEntity> materiasAtualizadas = this.materiaRepository.findAll();

        assertTrue(materias.getBody().getData());
        assertEquals(4, materiasAtualizadas.size());
        assertEquals(201, materias.getBody().getStatusCode());
    }

    @Test
    public void testeExcluirMateriaPorId() {
        List<MateriaEntity> materiasList = this.materiaRepository.findAll();
        Long id = materiasList.get(0).getId();

        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/" + id, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });

        List<MateriaEntity> materiasAtualizadas = this.materiaRepository.findAll();

        assertNotNull(materias.getBody().getData());
        assertEquals(2, materiasAtualizadas.size());
        assertEquals(200, materias.getBody().getStatusCode());
    }
}
