package com.zerobase.moy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"Report Controller 보고서(리포트) API"})

@RequestMapping("/report")
@RequiredArgsConstructor
@RestController
public class ReportController {

  private final ReportService reportService;

  @PostMapping("/{id}")
  @ApiOperation(value = "리포트 작성하기" , notes = "id 해당하는 다이어리를 외부 api를 사용하여 감정보고서를 작성합니다. (작성되어있지 않을경우) ")
  private ResponseEntity<?> postReport(@AuthenticationPrincipal User user, @Parameter(description = "Diary id") @PathVariable Long id)
      throws JsonProcessingException {
    var result = reportService.reportDiary(user, id);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_CREATED)
        .data(result)
        .build());
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "리포트 보기" , notes = "id 에대한 리포트를 확인합니다. (본인 보고서만 열람가능)")
  private ResponseEntity<?> getReport(@AuthenticationPrincipal User user,@Parameter(description = "Report id") @PathVariable Long id)
      throws JsonProcessingException {
    var result = reportService.getReport(user, id);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_CREATED)
        .data(result)
        .build());
  }

  @GetMapping("/my")
  @ApiOperation(value = "내 리포트 가져오기" , notes = "내 리포트를  모두 가져옵니다.")
  private ResponseEntity<?> getMyReport(@AuthenticationPrincipal User user,
      @RequestParam(defaultValue = "0") @ApiParam("시작  페이지") int page,
      @RequestParam(defaultValue = "10") @ApiParam("불러올 최대 갯수") int size,
      @RequestParam(defaultValue = "id") @ApiParam("칼럼이름 default: 문서 id")String sortType) {

    Sort sort = Sort.by(Direction.DESC, sortType);
    PageRequest pageRequest = PageRequest.of(page, size, sort);
    var result = reportService.getMyReports(user, pageRequest);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_CREATED)
        .data(result)
        .build());
  }
}
