package com.rasmoo.cliente.escola.gradecurricular.v1.service;

import java.util.List;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.v1.model.CursoModel;

public interface ICursoService {

    Boolean cadastrar(final CursoModel cursoModel);

    Boolean atualizar(final CursoModel cursoModel);

    Boolean excluir(final Long cursoId);

    CursoEntity consultarPorCodigo(final String codCurso);

    List<CursoEntity> listarCursos();
}
