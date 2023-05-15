package com.zerobase.moy.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode e) {
    super(e.getMsg());
    this.errorCode = e;
  }

  @Builder
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class CustomExceptionResponse { // 에러응답

    private int status;
    private String code;
    private String message;
  }


}
