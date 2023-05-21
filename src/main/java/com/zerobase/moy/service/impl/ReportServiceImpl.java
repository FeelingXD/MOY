package com.zerobase.moy.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.entity.Report;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.CLOVA.CLOVARequestDto;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import com.zerobase.moy.repository.DiaryRepository;
import com.zerobase.moy.repository.ReportRepository;
import com.zerobase.moy.response.exception.CustomException;
import com.zerobase.moy.response.exception.ErrorCode;
import com.zerobase.moy.service.ReportService;
import com.zerobase.moy.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

  private final DiaryRepository diaryRepository;
  private final ReportRepository reportRepository;
  private final WebClient CLOVA_client;
  @Override
  public SentimentResponse reportDiary(User user, Long id) throws JsonProcessingException {
    var diary= diaryRepository.findByIdAndUserIdAndReportedIsFalseAndDeletedIsFalse(id,user.getId()).orElseThrow(() -> new CustomException(
        ErrorCode.ALREADY_REPORTED));
    var content =diary.getTitle()+" "+diary.getContent();
    var result=getApiResponse(content).block();

    var report=Report.builder().diary(diary).json(content).build();
    diary.setReported(true);
    reportRepository.save(report);

    return JsonUtil.toSentimentResponse(content);
  }

  @Override
  public SentimentResponse getReport(User user, Long id) throws JsonProcessingException {
    var report = reportRepository.findByIdAndDiary_User_Id(id,user.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPORT));
    return JsonUtil.toSentimentResponse(report.getJson());
  }


  public Mono<String> getApiResponse(String content){
    var requestDto= CLOVARequestDto.builder().content(content).build();
    log.info(requestDto.getContent());
    return CLOVA_client.post()
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(requestDto)
        .retrieve().bodyToMono(String.class);
  }

}