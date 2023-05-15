package com.zerobase.moy.response.exception;

import com.zerobase.moy.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvise {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> customExceptionResponseEntity(CustomException e) {
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(ApiResponse.builder()
            .code(e.getErrorCode())
            .build()
        );
  }
}
