package com.rasmoo.cliente.escola.gradecurricular.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.rasmoo.cliente.escola.gradecurricular.exception.MateriaException;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorMapResponse;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorMapResponse.ErrorMapResponseBuilder;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorResponse;
import com.rasmoo.cliente.escola.gradecurricular.model.ErrorResponse.ErrorResponseBuilder;


@ControllerAdvice
public class ResourceHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
                Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            errors.put(campo, mensagem);
        });
        ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
        errorMap.errors(errors)
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timeStamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap.build());
    }

    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException ex) {
        ErrorResponseBuilder erro = ErrorResponse.builder();
        erro.httpStatus(ex.getHttpStatus().value());
        erro.mensagem(ex.getMessage());
        erro.timeStamp(System.currentTimeMillis());
        return ResponseEntity.status(ex.getHttpStatus()).body(erro.build());
    }

}
