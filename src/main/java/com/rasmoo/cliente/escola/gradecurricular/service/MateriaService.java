package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
public class MateriaService implements IMateriaService{

    @Autowired
    private IMateriaRepository materiaRepository;

    @Override
    public Boolean atualizar(MateriaEntity materia) {
        try {
            MateriaEntity materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();
            materiaAtualizada.setNome(materia.getNome());
            materiaAtualizada.setCodigo(materia.getCodigo());
            materiaAtualizada.setFrequencia(materia.getFrequencia());
            materiaAtualizada.setHoras(materia.getHoras());

            this.materiaRepository.save(materia);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean excluir(Long id) {
        try {
            this.materiaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
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
        return this.materiaRepository.findAll();
    }

    @Override
    public MateriaEntity listaMateriaPorId(Long id) {
        return this.materiaRepository.findById(id).get();
    }
}
