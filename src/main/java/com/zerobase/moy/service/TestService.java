package com.zerobase.moy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import com.zerobase.moy.response.exception.ClovaResponseException;
import com.zerobase.moy.service.impl.ReportServiceImpl;
import com.zerobase.moy.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

  private final ReportServiceImpl reportService;

  @Value("${CLOVA.test.response}")
  String response;
  @Value("${CLOVA.test.error}")
  String error;

  public SentimentResponse test() throws JsonProcessingException {
    return JsonUtil.fromJson(response,SentimentResponse.class);
  }

  public SentimentResponse testException() throws Exception {
    return JsonUtil.fromJson(error,SentimentResponse.class);
  }
  public void clovaExceptionTest() {

      log.info(reportService.getApiResponse("").block());
  }
}
