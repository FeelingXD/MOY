package com.zerobase.moy.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.model.CLOVA.SentimentErrorResponse;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import com.zerobase.moy.util.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

  @Value("${CLOVA.test.response}")
  String response;
  @Value("${CLOVA.test.error}")
  String error;
  public SentimentResponse test() throws JsonProcessingException {
    return JsonUtil.toSentimentResponse(response);
  }

  public SentimentResponse testException() throws Exception {
    return JsonUtil.toSentimentResponse(error);
  }
}
