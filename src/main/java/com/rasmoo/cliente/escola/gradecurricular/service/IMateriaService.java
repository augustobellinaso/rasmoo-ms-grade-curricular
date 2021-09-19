package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.List;
import com.rasmoo.cliente.escola.gradecurricular.dto.MateriaDTO;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;

public interface IMateriaService {

    Boolean atualizar(final MateriaDTO materia);

    Boolean excluir(final Long id);

    Boolean cadastrar(final MateriaDTO materia);

    List<MateriaDTO> listarTodas();

    MateriaDTO consultar(final Long id);

    List<MateriaDTO> listarPorHoraMinima(final int horaMinima);
}
