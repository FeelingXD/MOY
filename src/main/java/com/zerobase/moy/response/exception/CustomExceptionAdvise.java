package com.zerobase.moy.response.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.model.CLOVA.SentimentErrorResponse;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.spring.web.json.Json;

@Slf4j
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

  @ExceptionHandler(ClovaResponseException.class)
  public ResponseEntity<?> ClovaResponseExceptionResponseEntity(ClovaResponseException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.builder()
                .code(e.getErrorCode())
                .data(e.getResponse())
                .build()
        );
  }
}
