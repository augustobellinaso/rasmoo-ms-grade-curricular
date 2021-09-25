package com.rasmoo.cliente.escola.gradecurricular.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerUnitTest {

    @LocalServerPort
    private int port;

    @MockBean
    private IMateriaService materiaService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static MateriaDTO materiaDTO;

    @BeforeAll
    public static void init() {
        materiaDTO = new MateriaDTO();
        materiaDTO.setId(1L);
        materiaDTO.setNome("Introducao a linguagem de programacao");
        materiaDTO.setCodigo("ILP");
        materiaDTO.setFrequencia(2);
        materiaDTO.setHoras(64);

    }

    @Test
    void testListarMaterias() {
        Mockito.when(this.materiaService.listarTodas()).thenReturn(new ArrayList<MateriaDTO>());

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testConsultarMateriaPorId() {
        Mockito.when(this.materiaService.consultar(1L)).thenReturn(materiaDTO);

        ResponseEntity<Response<MateriaDTO>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia/1", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<MateriaDTO>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testAtualizarMateria() {
        Mockito.when(this.materiaService.atualizar(materiaDTO)).thenReturn(Boolean.TRUE);

        HttpEntity<MateriaDTO> request = new HttpEntity<>(materiaDTO);

        ResponseEntity<Response<Boolean>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia", HttpMethod.PUT, request,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testCadastrarMateria() {
        Mockito.when(this.materiaService.cadastrar(materiaDTO)).thenReturn(Boolean.TRUE);

        HttpEntity<MateriaDTO> request = new HttpEntity<>(materiaDTO);

        ResponseEntity<Response<Boolean>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia", HttpMethod.POST, request,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(201, materias.getBody().getStatusCode());
    }

    @Test
    void testExcluirMateria() {
        Mockito.when(this.materiaService.excluir(1L)).thenReturn(Boolean.TRUE);

        ResponseEntity<Response<Boolean>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia/1", HttpMethod.DELETE, null,
                        new ParameterizedTypeReference<Response<Boolean>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testConsultarMateriaPorHoraMinima() {
        Mockito.when(this.materiaService.listarPorHoraMinima(64)).thenReturn(new ArrayList<MateriaDTO>());

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia/horario-minimo/64", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    void testConsultarMateriaPorFrequencia() {
        Mockito.when(this.materiaService.listarPorFrequencia(2)).thenReturn(new ArrayList<MateriaDTO>());

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate
                .exchange("http://localhost:" + this.port + "/materia/frequencia/2", HttpMethod.GET, null,
                        new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                        });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }
}
