package com.rasmoo.cliente.escola.gradecurricular.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.rasmoo.cliente.escola.gradecurricular.constante.MensagensConstant;
import com.rasmoo.cliente.escola.gradecurricular.entity.CursoEntity;
import com.rasmoo.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.rasmoo.cliente.escola.gradecurricular.exception.CursoException;
import com.rasmoo.cliente.escola.gradecurricular.model.CursoModel;
import com.rasmoo.cliente.escola.gradecurricular.repository.ICursoRepository;
import com.rasmoo.cliente.escola.gradecurricular.repository.IMateriaRepository;

@Service
@CacheConfig(cacheNames = "curso")
public class CursoService implements ICursoService{

    private final ICursoRepository cursoRepository;
    private final IMateriaRepository materiaRepository;

    @Autowired
    public CursoService(ICursoRepository cursoRepository, IMateriaRepository materiaRepository) {
        this.cursoRepository = cursoRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Boolean cadastrar(CursoModel cursoModel) {
        try {
            if (cursoModel.getId() != null) {
                throw new CursoException(MensagensConstant.ERRO_ID_INFORMADO.getDescricao(), HttpStatus.BAD_REQUEST);
            }

            if (this.cursoRepository.findCursoByCodigo(cursoModel.getCodCurso()) != null) {
                throw new CursoException(MensagensConstant.ERRO_CURSO_CADASTRADO_ANTERIORMENTE.getDescricao(), HttpStatus.BAD_REQUEST);
            }
            return this.cadastrarOuAtualizar(cursoModel);
        } catch (CursoException c) {
            throw c;
        } catch (Exception e) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean atualizar(CursoModel cursoModel) {
        try {
            this.consultarPorCodigo(cursoModel.getCodCurso());
            return this.cadastrarOuAtualizar(cursoModel);
        } catch (CursoException c) {
            throw c;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean excluir(Long cursoId) {
        try {
            if(this.cursoRepository.findById(cursoId).isPresent()) {
                this.cursoRepository.deleteById(cursoId);
                return Boolean.TRUE;
            }
            throw new CursoException(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND);
        }catch (CursoException c) {
            throw c;
        }catch (Exception e) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CachePut(key = "#codCurso")
    public CursoEntity consultarPorCodigo(String codCurso) {
        try {
            CursoEntity curso = this.cursoRepository.findCursoByCodigo(codCurso);

            if (curso == null) {
                throw new CursoException(MensagensConstant.ERRO_CURSO_NAO_ENCONTRADO.getDescricao(), HttpStatus.NOT_FOUND);
            }

            return curso;
        } catch (CursoException c) {
            throw c;
        } catch (Exception e) {
            throw new CursoException(MensagensConstant.ERRO_GENERICO.getDescricao(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @CachePut(unless = "#result.size()<3")
    public List<CursoEntity> listarCursos() {
        return this.cursoRepository.findAll();
    }

    private Boolean cadastrarOuAtualizar(CursoModel cursoModel) {
        List<MateriaEntity> materiaEntityList = new ArrayList<>();

        if (!cursoModel.getMaterias().isEmpty()) {
            cursoModel.getMaterias().forEach(materia -> {
                if (this.materiaRepository.findById(materia).isPresent()) {
                    materiaEntityList.add(this.materiaRepository.findById(materia).get());
                }
            });
        }

        CursoEntity cursoEntity = new CursoEntity();
        if (cursoModel.getId() != null) {
            cursoEntity.setId(cursoModel.getId());
        }
        cursoEntity.setCodigo(cursoModel.getCodCurso());
        cursoEntity.setNome(cursoModel.getNome());
        cursoEntity.setMaterias(materiaEntityList);

        this.cursoRepository.save(cursoEntity);

        return Boolean.TRUE;
    }
}
