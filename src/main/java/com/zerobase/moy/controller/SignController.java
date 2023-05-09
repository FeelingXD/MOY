package com.zerobase.moy.controller;

import com.zerobase.moy.data.model.SignInRequestDto;
import com.zerobase.moy.data.model.SignUpRequestDto;
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
  public ResponseEntity<?> signIn(@RequestBody SignInRequestDto signInDto) {

    return ResponseEntity.ok().body(signService.signIn(signInDto));
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpDto) {

    return ResponseEntity.ok().body(signService.signUp(signUpDto));
  }
  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest req){
    return ResponseEntity.ok().body(signService.logout(req));
  }
  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(HttpServletRequest req){
    return ResponseEntity.ok().body(signService.reissue(req));
  }



}
