package com.rasmoo.cliente.escola.gradecurricular.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import com.rasmoo.cliente.escola.gradecurricular.constant.MensagensConstant;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class MateriaServiceUnitTest {

    @Mock
    private IMateriaRepository materiaRepository;

    @InjectMocks
    private MateriaService materiaService;

    private static MateriaEntity materiaEntity;

    @BeforeAll
    public static void onInit() {
        materiaEntity = new MateriaEntity();
        materiaEntity.setId(1L);
        materiaEntity.setNome("Introdução a lógica de programação");
        materiaEntity.setCodigo("ILP1");
        materiaEntity.setFrequencia(1);
        materiaEntity.setHoras(68);
    }

    //CASOS DE SUCESSO
    @Test
    void testListarSucesso() {
        List<MateriaEntity> listMateria = new ArrayList<>();
        listMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findAll()).thenReturn(listMateria);
        List<MateriaDTO> listMateriaDto = this.materiaService.listarTodas();

        assertNotNull(listMateriaDto);
        assertEquals("ILP1", listMateriaDto.get(0).getCodigo());
        assertEquals(1, listMateriaDto.get(0).getId());
        assertEquals("/materia/1", listMateriaDto.get(0).getLinks().getRequiredLink("self").getHref());
        assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findAll();

    }

    @Test
    void testListarHorarioMinimoSucesso() {
        List<MateriaEntity> listMateria = new ArrayList<>();
        listMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findByHoraMinima(60)).thenReturn(listMateria);
        List<MateriaDTO> listMateriaDto = this.materiaService.listarPorHoraMinima(60);

        assertNotNull(listMateriaDto);
        assertEquals("ILP1", listMateriaDto.get(0).getCodigo());
        assertEquals(1, listMateriaDto.get(0).getId());
        assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByHoraMinima(60);

    }

    @Test
    void testListarFrequenciaSucesso() {
        List<MateriaEntity> listMateria = new ArrayList<>();
        listMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findByFrequencia(1)).thenReturn(listMateria);
        List<MateriaDTO> listMateriaDto = this.materiaService.listarPorFrequencia(1);

        assertNotNull(listMateriaDto);
        assertEquals("ILP1", listMateriaDto.get(0).getCodigo());
        assertEquals(1, listMateriaDto.get(0).getId());
        assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByFrequencia(1);

    }

    @Test
    void testeConsultaSucesso() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        MateriaDTO materiaDTO = this.materiaService.consultar(1L);

        assertNotNull(materiaDTO);
        assertEquals("ILP1", materiaDTO.getCodigo());
        assertEquals(1, materiaDTO.getId());
        assertEquals(1, materiaDTO.getFrequencia());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testeCadastrarSucesso() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);

        materiaEntity.setId(null);

        Mockito.when(this.materiaRepository.findByCodigo("ILP1")).thenReturn(null);
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

        Boolean sucesso = this.materiaService.cadastrar(materiaDTO);

        assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByCodigo("ILP1");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);

        materiaEntity.setId(1L);

    }

    @Test
    void testeAtualizarSucesso() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setId(1L);
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);


        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

        Boolean sucesso = this.materiaService.atualizar(materiaDTO);

        assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);
    }

    @Test
    void testeExcluirSucesso() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Boolean sucesso = this.materiaService.excluir(1L);

        assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(0)).findByCodigo("ILP1");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).deleteById(1L);
    }

    //CENARIOS DE THROW MATERIA EXCEPTION
    @Test
    void testeAtualizarThrowMateriaException() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setId(1L);
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);


        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.atualizar(materiaDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }

    @Test
    void testeExcluirThrowMateriaException() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.excluir(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(0)).deleteById(1L);
    }

    @Test
    void testeCadastrarComIdThrowMateriaException() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setId(1L);
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrar(materiaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_ID_INFORMADO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(0)).findByCodigo("ILP1");
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }

    @Test
    void testeCadastrarComCodigoExistenteThrowMateriaException() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);

        Mockito.when(this.materiaRepository.findByCodigo("ILP1")).thenReturn(materiaEntity);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrar(materiaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByCodigo("ILP1");
        Mockito.verify(this.materiaRepository, Mockito.times(0)).save(materiaEntity);
    }

    //CENARIOS DE THROW EXCEPTION
    @Test
    void testeAtualizarThrowException() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setId(1L);
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);


        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.atualizar(materiaDTO);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);
    }

    @Test
    void testeCadastrarThrowException() {
        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setNome("Introdução a lógica de programação");
        materiaDTO.setCodigo("ILP1");
        materiaDTO.setFrequencia(1);
        materiaDTO.setHoras(68);

        materiaEntity.setId(null);

        Mockito.when(this.materiaRepository.findByCodigo("ILP1")).thenReturn(null);
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrar(materiaDTO);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(0)).findById(1L);
        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByCodigo("ILP1");
        Mockito.verify(this.materiaRepository, Mockito.times(1)).save(materiaEntity);

        materiaEntity.setId(1L);
    }

    @Test
    void testeConsultarThrowException() {
        Mockito.when(this.materiaRepository.findById(1L)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.consultar(1L);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testeListarThrowException() {
        Mockito.when(this.materiaRepository.findAll()).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.listarTodas();
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testeListarPorFrequenciaThrowException() {
        Mockito.when(this.materiaRepository.findByFrequencia(1)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.listarPorFrequencia(1);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByFrequencia(1);
    }

    @Test
    void testeListarPorHoraMinimaThrowException() {
        Mockito.when(this.materiaRepository.findByHoraMinima(34)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.listarPorHoraMinima(34);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).findByHoraMinima(34);
    }

    @Test
    void testeExcluirThrowException() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1L);

        MateriaException materiaException;

        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.excluir(1L);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getDescricao(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, Mockito.times(1)).deleteById(1L);
    }
}
