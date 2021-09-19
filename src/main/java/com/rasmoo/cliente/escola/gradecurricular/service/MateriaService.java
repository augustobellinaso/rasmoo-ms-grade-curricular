package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

@Service
public class MateriaService implements IMateriaService {

    private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
    private static final String MATERIA_NAO_ENCONTRADA = "Matéria não encontrada";

    private final IMateriaRepository materiaRepository;
    private final ModelMapper mapper;

    @Autowired
    public MateriaService(IMateriaRepository materiaRepository) {
        this.mapper = new ModelMapper();
        this.materiaRepository = materiaRepository;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "materia", key = "#materia.id"),
            @CacheEvict(value = "escola", key = "#materia.id")
    })
    public Boolean atualizar(MateriaDTO materia) {
        try {
            this.consultar(materia.getId());
            MateriaEntity materiaEntityAtualizada = this.mapper.map(materia, MateriaEntity.class);
            this.materiaRepository.save(materiaEntityAtualizada);

            return Boolean.TRUE;

        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw e;
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
            throw e;
        }
    }

    @Override
    public Boolean cadastrar(MateriaDTO materia) {
        try {
            MateriaEntity materiaEntity = this.mapper.map(materia, MateriaEntity.class);
            this.materiaRepository.save(materiaEntity);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<MateriaDTO> listarTodas() {
        try {
            return this.mapper.map(this.materiaRepository.findAll(), new TypeToken<List<MateriaDTO>>() {
            }.getType());
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "materia", key = "#id")
    public MateriaDTO consultar(Long id) {
        try {
            Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
            if (materiaOptional.isPresent()) {
                return this.mapper.map(materiaOptional.get(), MateriaDTO.class);
            }
            throw new MateriaException(MATERIA_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
