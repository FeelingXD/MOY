package com.zerobase.moy.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
  RESPONSE_DELETED(3, "DELETED"),
  RESPONSE_CREATED(2, "CREATED"),
  RESPONSE_SUCCESS(1, "SUCCESS"),
  RESPONSE_FAIL(0, "FAIL");

  private final int status;
  private final String msg;
}
