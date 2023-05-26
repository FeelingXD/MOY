package com.zerobase.moy.data.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import javax.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class ReportJsonConverter implements AttributeConverter<SentimentResponse, String> {

  private final ObjectMapper objectMapper;

  @Override
  public String convertToDatabaseColumn(SentimentResponse attribute) {
    if (ObjectUtils.isEmpty(attribute)) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }


  }

  @Override
  public SentimentResponse convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isEmpty()) {
      return null;
    }
    try {
      return objectMapper.readValue(dbData, SentimentResponse.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
