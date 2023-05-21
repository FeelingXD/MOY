package com.zerobase.moy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.TestService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

  private final TestService testService;

  @GetMapping()
  public ResponseEntity<?> test(HttpServletRequest req) {
    System.out.println(req);
    return ResponseEntity.ok().body("ok");
  }

  @GetMapping("/user")
  public ResponseEntity<?> userAthentic(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok().body(user.getId());
  }

  @GetMapping("/report")
  public ResponseEntity<?> testReport() throws JsonProcessingException {
    var result = testService.test();
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(result)
        .build());
  }

  @GetMapping("/reportException")
  public ResponseEntity<?> testException() throws Exception {
    var result = testService.testException();
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .data(result)
        .build());
  }

  @GetMapping("/clovaException")
  public ResponseEntity<?> testClovaException() throws Exception {
    testService.clovaExceptionTest();
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .build());
  }
}
