package com.rasmoo.cliente.escola.gradecurricular.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class MateriaException extends RuntimeException {

    private final HttpStatus httpStatus;

    public MateriaException(final String mensagem, final HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
    }
}
