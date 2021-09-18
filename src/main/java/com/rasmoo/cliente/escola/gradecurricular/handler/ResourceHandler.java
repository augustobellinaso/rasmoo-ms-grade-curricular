package com.rasmoo.cliente.escola.gradecurricular.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorResponse;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorResponse.ErrorResponseBuilder;

@ControllerAdvice
public class ResourceHandler {

    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException ex) {
        ErrorResponseBuilder erro = ErrorResponse.builder();
        erro.httpStatus(ex.getHttpStatus().value());
        erro.mensagem(ex.getMessage());
        erro.timeStamp(System.currentTimeMillis());
        return ResponseEntity.status(ex.getHttpStatus()).body(erro.build());
    }

}
