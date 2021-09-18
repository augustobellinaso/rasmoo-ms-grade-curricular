package com.rasmoo.cliente.escola.gradecurricular.entity;

import java.io.Serializable;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_materia")
@Data
@NoArgsConstructor
public class MateriaEntity implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "nome")
    private String nome;

    @Column(name = "hrs")
    private int horas;

    @Column(name = "cod")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String codigo;

    @Column(name = "freq")
    private int frequencia;


}
