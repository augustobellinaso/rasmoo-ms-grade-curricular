package com.rasmoo.cliente.escola.gradecurricular.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tb_curso")
public class CursoEntity implements Serializable {

    @Id
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "nome")
    private String nome;

    @Column(name = "cod")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String codigo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private List<MateriaEntity> materias;
}
