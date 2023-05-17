package com.zerobase.moy.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@Getter
public class ApiResponse<T> {

  private String code;
  private HttpStatus status;
  private String msg;
  private T data;


  @Builder
  public ApiResponse(Code code, T data) {
    this.code = code.getCode();
    this.status = code.getStatus();
    this.msg = code.getMsg();
    this.data = data;
  }

}
