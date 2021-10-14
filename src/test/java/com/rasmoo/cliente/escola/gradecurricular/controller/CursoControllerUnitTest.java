package com.rasmoo.cliente.escola.gradecurricular.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;
import com.rasmoo.cliente.escola.gradecurricular.service.ICursoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@SpringBootTest
@AutoConfigureMockMvc
public class CursoControllerUnitTest {

    @MockBean
    private ICursoService cursoService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private static final String USER = "rasmoo";
    private static final String PASSWORD = "msgradecurricular";

    @Test
    void testCadastrarCurso() throws Exception {
        CursoModel cursoModel = new CursoModel();
        cursoModel.setNome("nome da disciplina");
        cursoModel.setCodCurso("Cod");
        cursoModel.setId(1L);
        cursoModel.setMaterias(new ArrayList<>());
        Mockito.when(this.cursoService.cadastrar(cursoModel)).thenReturn(true);

        mvc.perform(post("/curso")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cursoModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("true"));

    }

    @Test
    void testListarCursos() throws Exception {
        mvc.perform(get("/curso")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testConsultarCursoPorId() throws Exception {
        mvc.perform(get("/curso/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testAtualizarCurso() throws Exception {
        CursoModel cursoModel = new CursoModel();
        cursoModel.setNome("nome da disciplina");
        cursoModel.setCodCurso("Cod");
        cursoModel.setId(1L);
        cursoModel.setMaterias(new ArrayList<>());
        Mockito.when(this.cursoService.atualizar(cursoModel)).thenReturn(true);

        mvc.perform(put("/curso")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cursoModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("true"));
    }

    @Test
    void testExcluirCurso() throws Exception {
        mvc.perform(delete("/curso/{id}", 1)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic(USER, PASSWORD))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
