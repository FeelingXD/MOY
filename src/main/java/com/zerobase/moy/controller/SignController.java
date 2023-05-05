package com.zerobase.moy.controller;

import com.zerobase.moy.data.model.SignInRequestDto;
import com.zerobase.moy.data.model.SignUpRequestDto;
import com.zerobase.moy.service.SignService;
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


}
