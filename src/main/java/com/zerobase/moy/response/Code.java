package com.zerobase.moy.response;

import org.springframework.http.HttpStatus;

public interface Code {

  String code = null;
  HttpStatus status = null;
  String msg = null;

  default String getCode() {
    return this.toString();
  }

  HttpStatus getStatus();

  String getMsg();

}
