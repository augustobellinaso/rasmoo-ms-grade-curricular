package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;

public interface IMateriaService {

    Boolean atualizar(final MateriaEntity materia);

    Boolean excluir(final Long id);

    Boolean cadastrar(final MateriaEntity materia);

    List<MateriaEntity> listarTodas();

    MateriaEntity consultar(final Long id);
}
