package com.rasmoo.cliente.escola.gradecurricular.v1.service;

import java.util.List;
import com.rasmoo.cliente.escola.gradecurricular.v1.dto.MateriaDTO;

public interface IMateriaService {

    Boolean atualizar(final MateriaDTO materia);

    Boolean excluir(final Long id);

    Boolean cadastrar(final MateriaDTO materia);

    List<MateriaDTO> listarTodas();

    MateriaDTO consultar(final Long id);

    List<MateriaDTO> listarPorHoraMinima(final int horaMinima);

    List<MateriaDTO> listarPorFrequencia(final int freq);
}
