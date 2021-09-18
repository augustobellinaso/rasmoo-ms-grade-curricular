package com.rasmoo.cliente.escola.gradecurricular.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErroResponse {

    private String mensagem;
    private int httpStatus;
    private long timeStamp;
}
