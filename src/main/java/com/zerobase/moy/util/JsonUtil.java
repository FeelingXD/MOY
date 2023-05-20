package com.zerobase.moy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.moy.data.model.CLOVA.SentimentErrorResponse;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {

  private final ObjectMapper objectMapper=new ObjectMapper();
  public static SentimentResponse toSentimentResponse(String json) throws JsonProcessingException {
    return objectMapper.readValue(json, SentimentResponse.class);
  }
  public static SentimentErrorResponse toSentimentErrorResponse(String json) throws Exception {
    return objectMapper.readValue(json, SentimentErrorResponse.class);
  }

}
