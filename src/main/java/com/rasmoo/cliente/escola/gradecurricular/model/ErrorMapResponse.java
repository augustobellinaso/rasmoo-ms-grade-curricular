package com.rasmoo.cliente.escola.gradecurricular.model;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorMapResponse {

    private int httpStatus;
    private Map<String, String> errors;
    private long timeStamp;
}
