package com.zerobase.moy.controller;

import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.data.model.sign.SignInForm;
import com.zerobase.moy.data.model.sign.SignUpForm;
import com.zerobase.moy.service.SignService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-api")
public class SignController {

  private final SignService signService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@RequestBody SignInForm form) {

    var result = signService.signIn(SignInForm.toDto(form));

    return ResponseEntity.accepted().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build()
    );
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpForm form) {
    signService.signUp(SignUpForm.toDto(form));
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .build());
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest req) {
    var result = signService.logout(req);

    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build());
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(HttpServletRequest req) {
    var result = signService.logout(req);

    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build()
    );
  }


}
