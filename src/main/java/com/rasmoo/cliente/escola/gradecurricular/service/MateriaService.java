package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class MateriaService implements IMateriaService {

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public Boolean atualizar(MateriaEntity materia) {
        try {
            MateriaEntity materiaAtualizada = this.consultar(materia.getId());

            materiaAtualizada.setNome(materia.getNome());
            materiaAtualizada.setCodigo(materia.getCodigo());
            materiaAtualizada.setFrequencia(materia.getFrequencia());
            materiaAtualizada.setHoras(materia.getHoras());

            this.materiaRepository.save(materia);


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
    public Boolean cadastrar(MateriaEntity materia) {
        try {
            this.materiaRepository.save(materia);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MateriaEntity> listarTodas() {
        try {
            return this.materiaRepository.findAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public MateriaEntity consultar(Long id) {
        try {
            Optional<MateriaEntity> materiaOptional = this.materiaRepository.findById(id);
            return materiaOptional.orElseThrow(() -> new MateriaException("Matéria não encontrada", HttpStatus.NOT_FOUND));

        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException("Erro interno identificado. Contate o suporte", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
