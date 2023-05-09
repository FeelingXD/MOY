package com.zerobase.moy.service;

import com.zerobase.moy.data.model.LogoutResultDto;
import com.zerobase.moy.data.model.ReissueResultDto;
import com.zerobase.moy.data.model.SignInRequestDto;
import com.zerobase.moy.data.model.SignInResultDto;
import com.zerobase.moy.data.model.SignUpRequestDto;
import com.zerobase.moy.data.model.SignUpResultDto;
import javax.servlet.http.HttpServletRequest;

public interface SignService {

  SignUpResultDto signUp(SignUpRequestDto dto);

  SignInResultDto signIn(SignInRequestDto dto) throws RuntimeException;

  LogoutResultDto logout(HttpServletRequest req);
  ReissueResultDto reissue(HttpServletRequest req);
}
