package com.zerobase.moy.controller;

import com.zerobase.moy.data.model.sign.SignInForm;
import com.zerobase.moy.data.model.sign.SignUpForm;
import com.zerobase.moy.response.ApiResponse;
import com.zerobase.moy.response.ResponseCode;
import com.zerobase.moy.service.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Sign Controller 로그인 API"})

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-api")
public class SignController {

  private final SignService signService;

  @PostMapping("/sign-in")
  @ApiOperation(value = "로그인" , notes = "로그인")
  public ResponseEntity<?> signIn(@RequestBody @Validated SignInForm form) {

    var result = signService.signIn(SignInForm.toDto(form));

    return ResponseEntity.accepted().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build()
    );
    
  }

  @PostMapping("/sign-up")
  @ApiOperation(value = "회원 가입" , notes = "회원 가입기능")
  public ResponseEntity<?> signUp(@RequestBody @Validated SignUpForm form) {
    signService.signUp(SignUpForm.toDto(form));
    return ResponseEntity.ok().body(ApiResponse.builder()
        .code(ResponseCode.RESPONSE_SUCCESS)
        .build());
  }

  @PostMapping("/logout")
  @ApiOperation(value = "로그아웃" , notes = "로그아웃 기능입니다. ATK 와 RTK 를 사용할수없도록 블랙리스트 합니다.")
  public ResponseEntity<?> logout(HttpServletRequest req) {
    var result = signService.logout(req);

    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build());
  }

  @PostMapping("/reissue")
  @ApiOperation(value = "리이슈" , notes = "ATK 토큰만료시 RTK를 확인하여서 새로운 ATK 를 발급합니다.")
  public ResponseEntity<?> reissue(HttpServletRequest req) {
    var result = signService.reissue(req);

    return ResponseEntity.ok().body(
        ApiResponse.builder()
            .code(ResponseCode.RESPONSE_SUCCESS)
            .data(result)
            .build()
    );
  }


}
