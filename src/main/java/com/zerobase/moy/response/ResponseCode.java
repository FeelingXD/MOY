package com.zerobase.moy.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode implements Code {
  RESPONSE_DELETED(HttpStatus.NO_CONTENT, "DELETED"),
  RESPONSE_CREATED(HttpStatus.CREATED, "CREATED"),
  RESPONSE_SUCCESS(HttpStatus.OK, "SUCCESS"),
  RESPONSE_FAIL(HttpStatus.BAD_REQUEST, "FAIL");


  private final HttpStatus status;
  private final String msg;

}
