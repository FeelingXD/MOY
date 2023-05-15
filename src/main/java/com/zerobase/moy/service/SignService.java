package com.zerobase.moy.service;

import com.zerobase.moy.data.model.sign.LogoutResultDto;
import com.zerobase.moy.data.model.sign.ReissueResultDto;
import com.zerobase.moy.data.model.sign.SignInRequestDto;
import com.zerobase.moy.data.model.sign.SignInResultDto;
import com.zerobase.moy.data.model.sign.SignUpRequestDto;
import javax.servlet.http.HttpServletRequest;

public interface SignService {

  void signUp(SignUpRequestDto dto);

  SignInResultDto signIn(SignInRequestDto dto) throws RuntimeException;

  LogoutResultDto logout(HttpServletRequest req);

  ReissueResultDto reissue(HttpServletRequest req);
}
