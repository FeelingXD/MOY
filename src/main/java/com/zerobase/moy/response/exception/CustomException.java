package com.zerobase.moy.response.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode e) {
    super(e.getMsg());
    this.errorCode = e;
  }
}
