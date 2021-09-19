package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;
import com.rasmoo.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
@CacheConfig(cacheNames = "curso")
public class CursoService implements ICursoService{

    private ICursoRepository cursoRepository;
    private IMateriaRepository materiaRepository;

    @Autowired
    public CursoService(ICursoRepository cursoRepository, IMateriaRepository materiaRepository) {
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Boolean cadastrar(CursoModel cursoModel) {
        return null;
    }

    @Override
    public Boolean atualizar(CursoModel cursoModel) {
        return null;
    }

    @Override
    public Boolean excluir(Long cursoId) {
        return null;
    }

    @Override
    public CursoEntity consultarPorCodigo(String codCurso) {
        return null;
    }

    @Override
    public List<CursoEntity> listarCursos() {
        return null;
    }
}
