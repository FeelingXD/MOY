package com.zerobase.moy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvise {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> customExceptionResponseEntity(CustomException e) {
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(CustomException.CustomExceptionResponse.builder()
            .message(e.getMessage())
            .code(e.getErrorCode().toString())
            .build());
  }
}
