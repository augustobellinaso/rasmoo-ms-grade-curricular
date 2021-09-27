package com.rasmoo.cliente.escola.gradecurricular.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.Response;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.rasmoo.cliente.escola.gradecurricular.service.IMateriaService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private static MateriaDTO materiaDto;

    @BeforeAll
    public static void init() {

        materiaDto = new MateriaDTO();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMCAO");
    }
    @Test
    public void testListarMaterias() {
        Mockito.when(this.materiaService.listarTodas()).thenReturn(new ArrayList<MateriaDTO>());

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testConsultarMaterias() {
        Mockito.when(this.materiaService.consultar(1L)).thenReturn(materiaDto);

        ResponseEntity<Response<MateriaDTO>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/1", HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<MateriaDTO>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testCadastrarMaterias() {
        Mockito.when(this.materiaService.cadastrar(materiaDto)).thenReturn(Boolean.TRUE);

        HttpEntity<MateriaDTO> request = new HttpEntity<>(materiaDto);

        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.POST, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(201, materias.getBody().getStatusCode());
    }

    @Test
    public void testAtualizarMaterias() {
        Mockito.when(this.materiaService.atualizar(materiaDto)).thenReturn(Boolean.TRUE);

        HttpEntity<MateriaDTO> request = new HttpEntity<>(materiaDto);

        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.PUT, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testExcluitMaterias() {
        Mockito.when(this.materiaService.excluir(1L)).thenReturn(Boolean.TRUE);

        ResponseEntity<Response<Boolean>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/1", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testConsultarMateriasPorHoraMinima() {
        Mockito.when(this.materiaService.listarPorHoraMinima(64)).thenReturn(new ArrayList<MateriaDTO>());

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/horario-minimo/64", HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testConsultarMateriasPorFrequencia() {
        Mockito.when(this.materiaService.listarPorFrequencia(1)).thenReturn(new ArrayList<MateriaDTO>());

        ResponseEntity<Response<List<MateriaDTO>>> materias = restTemplate.exchange(
                "http://localhost:" + this.port + "/materia/frequencia/1", HttpMethod.GET, null,
                new ParameterizedTypeReference<Response<List<MateriaDTO>>>() {
                });
        assertNotNull(materias.getBody().getData());
        assertEquals(200, materias.getBody().getStatusCode());
    }
}
