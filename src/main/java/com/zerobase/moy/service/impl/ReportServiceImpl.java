package com.zerobase.moy.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.entity.Diary;
import com.zerobase.moy.data.entity.Report;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.CLOVA.CLOVARequestDto;
import com.zerobase.moy.data.model.CLOVA.SentimentErrorResponse;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import com.zerobase.moy.repository.jpa.DiaryRepository;
import com.zerobase.moy.repository.jpa.ReportRepository;
import com.zerobase.moy.response.exception.ClovaResponseException;
import com.zerobase.moy.response.exception.CustomException;
import com.zerobase.moy.response.exception.ErrorCode;
import com.zerobase.moy.service.ReportService;
import com.zerobase.moy.util.JsonUtil;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
  private final WebClient clovaClient;

  @Override
  public SentimentResponse reportDiary(User user, Long id) throws JsonProcessingException {
    var diary = diaryRepository.findByIdAndUserIdAndReportedIsFalseAndDeletedIsFalse(id,
        user.getId()).orElseThrow(() -> new CustomException(
        ErrorCode.ALREADY_REPORTED));

    var content = getContent(diary);

    var result = getApiResponse(content).block();

    var report = Report.builder()
        .diary(diary)
        .json(result)
        .build();
    diary.setReported(true);


      diaryRepository.save(diary);
      reportRepository.save(report);
      return JsonUtil.fromJson(result, SentimentResponse.class);
  }

  private String getContent(Diary diary) {
    return diary.getTitle() + " " + diary.getContent();
  }

  @Override
  public SentimentResponse getReport(User user, Long id) throws JsonProcessingException {
    var report = reportRepository.findByIdAndDiary_User_Id(id, user.getId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPORT));
    return JsonUtil.fromJson(report.getJson(), SentimentResponse.class);
  }

  @Override
  public Page<SentimentResponse> getMyReports(User user, Pageable pageable) {
    var result = reportRepository.getMyReports(user.getId(),pageable)
        .stream()
        .map(e-> {
          try {
            return JsonUtil.fromJson(e.getJson(),SentimentResponse.class);
          } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
          }
        }).collect(Collectors.toList());

    return new PageImpl<>(result);
  }


  public Mono<String> getApiResponse(String content) {
    var requestDto = CLOVARequestDto.builder().content(content).build();
    return clovaClient.post()
        .accept(MediaType.APPLICATION_JSON)
        .bodyValue(requestDto)
        .retrieve()
        .onStatus(HttpStatus::isError, response ->
            response.bodyToMono(String.class)
                .flatMap(errorResponse -> {
                  try {
                    var clovaErrorResponse = JsonUtil.fromJson(errorResponse,
                        SentimentErrorResponse.class);
                    return Mono.error(new ClovaResponseException(ErrorCode.CLOVA_RESPONSE_ERROR,
                        clovaErrorResponse));
                  } catch (JsonProcessingException e) {
                    return Mono.error(new CustomException(ErrorCode.JSON_PROCESS_ERROR));
                  }
                })).bodyToMono(String.class);

  }
}
