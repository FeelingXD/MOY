package com.zerobase.moy.exception.diary;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode e) {
    super();
    this.errorCode = e;
  }

  @Builder
  @Getter
  @Setter
  @NoArgsConstructor
  public static class CustomExceptionResponse { //에러 리스폰스

  }


}
