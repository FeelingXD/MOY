package com.zerobase.moy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/report")
@RequiredArgsConstructor
@RestController
public class ReportController {
  private final ReportService reportService;

  @PostMapping("/{id}")
  private ResponseEntity<?> postReport(@AuthenticationPrincipal User user,@PathVariable Long id)
      throws JsonProcessingException {
    var result=reportService.reportDiary(user,id);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_CREATED)
        .data(result)
        .build());
  }

  @GetMapping("/{id}")
  private ResponseEntity<?> getReport(@AuthenticationPrincipal User user,@PathVariable Long id)
      throws JsonProcessingException {
    var result=reportService.getReport(user,id);
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_CREATED)
        .data(result)
        .build());
  }
}
