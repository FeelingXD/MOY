package com.zerobase.moy.response.exception;

import com.zerobase.moy.data.model.CLOVA.SentimentErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClovaResponseException extends CustomException {

  private SentimentErrorResponse response;

  public ClovaResponseException(ErrorCode e, SentimentErrorResponse response) {
    super(e);
    this.response = response;
  }
}
