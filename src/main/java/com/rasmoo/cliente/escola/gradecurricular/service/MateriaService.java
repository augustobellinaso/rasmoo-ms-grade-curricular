package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public Boolean atualizar(MateriaDTO materia) {
        try {
            this.consultar(materia.getId());
            ModelMapper mapper = new ModelMapper();
            MateriaEntity materiaEntityAtualizada = mapper.map(materia, MateriaEntity.class);
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
            return true;
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean cadastrar(MateriaDTO materia) {
        try {
            ModelMapper mapper = new ModelMapper();
            MateriaEntity materiaEntity = mapper.map(materia, MateriaEntity.class);
            this.materiaRepository.save(materiaEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MateriaDTO> listarTodas() {
        try {
            ModelMapper mapper = new ModelMapper();
            return mapper.map(this.materiaRepository.findAll(), new TypeToken<List<MateriaDTO>>() {}.getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public MateriaDTO consultar(Long id) {
        try {
            ModelMapper mapper = new ModelMapper();
            Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
            if (materiaOptional.isPresent()) {
                return mapper.map(materiaOptional.get(), MateriaDTO.class);
            }
            throw new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException("Erro interno identificado. Contate o suporte", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
