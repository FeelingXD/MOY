package com.zerobase.moy.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@AllArgsConstructor
@Getter
public class ApiResponse<T> {

  private int code;
  private String msg;
  private T data;


  @Builder
  public ApiResponse(ResponseCode code, T data) {
    this.code = code.getStatus();
    this.msg = code.getMsg();
    this.data = data;
  }

}
