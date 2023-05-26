package com.zerobase.moy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

  SentimentResponse reportDiary(User user, Long id) throws JsonProcessingException;

  SentimentResponse getReport(User user, Long id) throws JsonProcessingException;

  Page<SentimentResponse> getMyReports(User user, Pageable pageable);
}
