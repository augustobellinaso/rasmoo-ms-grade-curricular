package com.rasmoo.cliente.escola.gradecurricular.v1.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.rasmoo.cliente.escola.gradecurricular.v1.constant.MensagensConstant;
import com.rasmoo.cliente.escola.gradecurricular.v1.controller.MateriaController;
import com.rasmoo.cliente.escola.gradecurricular.v1.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.v1.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

@Service
@CacheConfig(cacheNames = "materia")
public class MateriaService implements IMateriaService {

    private final IMateriaRepository materiaRepository;
    private final ModelMapper mapper;

    @Autowired
    public MateriaService(IMateriaRepository materiaRepository) {
        this.mapper = new ModelMapper();
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Boolean atualizar(MateriaDTO materia) {
        try {
            this.consultar(materia.getId());
            return this.cadastrarOuAtualizar(materia);

        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean excluir(Long id) {
        try {
            this.consultar(id);
            this.materiaRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean cadastrar(MateriaDTO materia) {
        try {
            if (materia.getId() != null) {
                throw new MateriaException(MensagensConstant.ERRO_ID_INFORMADO.getDescricao(), HttpStatus.BAD_REQUEST);
            }

            if (this.materiaRepository.findByCodigo(materia.getCodigo()) != null) {
                throw new MateriaException(MensagensConstant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getDescricao(), HttpStatus.BAD_REQUEST);
            }

            return this.cadastrarOuAtualizar(materia);

        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CachePut(unless = "#result.size() < 3")
    public List<MateriaDTO> listarTodas() {
        try {
            List<MateriaDTO> materiaDTO = this.mapper.map(this.materiaRepository.findAll(), new TypeToken<List<MateriaDTO>>() {
            }.getType());

            materiaDTO.forEach(materia -> materia.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                            .consultarMateria(materia.getId()))
                    .withSelfRel()));
            return materiaDTO;
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CachePut(key = "#id")
    public MateriaDTO consultar(Long id) {
        try {
            Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
            if (materiaOptional.isPresent()) {
                return this.mapper.map(materiaOptional.get(), MateriaDTO.class);
            }
            throw new MateriaException(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getDescricao(), HttpStatus.NOT_FOUND);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<MateriaDTO> listarPorHoraMinima(int horaMinima) {
        try {
            return this.mapper.map(this.materiaRepository.findByHoraMinima(horaMinima), new TypeToken<List<MateriaDTO>>() {
            }.getType());
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<MateriaDTO> listarPorFrequencia(int freq) {
        try {
            return this.mapper.map(this.materiaRepository.findByFrequencia(freq), new TypeToken<List<MateriaDTO>>() {
            }.getType());
        } catch (Exception e) {
            throw new MateriaException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Boolean cadastrarOuAtualizar(MateriaDTO materiaDTO) {
        MateriaEntity materiaEntity = this.mapper.map(materiaDTO, MateriaEntity.class);
        this.materiaRepository.save(materiaEntity);
        return Boolean.TRUE;
    }
}
