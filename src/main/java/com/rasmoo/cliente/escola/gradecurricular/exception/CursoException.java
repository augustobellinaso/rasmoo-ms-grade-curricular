package com.rasmoo.cliente.escola.gradecurricular.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class CursoException extends RuntimeException{

    private final HttpStatus httpStatus;

    public CursoException(final String mensagem, final HttpStatus httpStatus) {
        super(mensagem);
        this.httpStatus = httpStatus;
    }
}

