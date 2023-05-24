package com.zerobase.moy.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtil {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public <T> T fromJson(String json, Class<T> valueType) throws JsonProcessingException {
    return objectMapper.readValue(json, valueType);
  }

}
