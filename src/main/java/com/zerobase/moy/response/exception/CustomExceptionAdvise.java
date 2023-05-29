package com.zerobase.moy.response.exception;

import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
  public ResponseEntity<?> ClovaResponseExceptionResponseEntity(ClovaResponseException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiResponse.builder()
                .code(e.getErrorCode())
                .data(e.getResponse())
                .build()
        );
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentNotValidException e){
    BindingResult result = e.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    String errorMessage = fieldErrors.stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.joining(","));

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.builder()
            .code(ResponseCode.RESPONSE_FAIL)
            .data(errorMessage)
            .build());
  }
}
