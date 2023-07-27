package com.example.jpa.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerResource {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ResponseDto.<Void>builder()
                        .code(-3)
                        .message("Validation Error")
                        .error(e.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> {
                                    String field = fieldError.getField();
                                    String message = fieldError.getDefaultMessage();
                                    String rejectionValue = String.valueOf(fieldError.getRejectedValue());
                                    return new ErrorDto(field, String.format("message :: %s, rejection :: %s", message, rejectionValue));
                                }).toList())
                .build());

    }
}
