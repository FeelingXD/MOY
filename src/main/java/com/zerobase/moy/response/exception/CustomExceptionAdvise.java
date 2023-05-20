package com.zerobase.moy.response.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.model.CLOVA.SentimentErrorResponse;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import springfox.documentation.spring.web.json.Json;

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
  @ExceptionHandler(JsonProcessingException.class)
  public  ResponseEntity<?> JsonExceptionResponseEntity(JsonProcessingException e)
      throws Exception {
    String rawContent = e.getLocation().contentReference().getRawContent().toString();
    SentimentErrorResponse errorResponse = JsonUtil.toSentimentErrorResponse(rawContent);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.builder()
                .code(ErrorCode.CLOVA_RESPONSE_ERROR)
                .data(errorResponse)
                .build()
        );
  }
}
