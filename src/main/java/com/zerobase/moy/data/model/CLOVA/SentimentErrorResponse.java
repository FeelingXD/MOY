package com.zerobase.moy.data.model.CLOVA;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SentimentErrorResponse {

  private int status;

  private ErrorDetail error;


  @Getter
  @Setter
  public static class ErrorDetail {

    private String errorCode;

    private String message;

  }
}