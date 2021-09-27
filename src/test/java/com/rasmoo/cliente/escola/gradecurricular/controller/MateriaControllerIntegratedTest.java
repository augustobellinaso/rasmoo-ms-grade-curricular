package com.rasmoo.cliente.escola.gradecurricular.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
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
}
