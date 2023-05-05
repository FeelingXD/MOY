package com.zerobase.moy.service;

import com.zerobase.moy.data.model.SignInRequestDto;
import com.zerobase.moy.data.model.SignInResultDto;
import com.zerobase.moy.data.model.SignUpRequestDto;
import com.zerobase.moy.data.model.SignUpResultDto;

public interface SignService {

  SignUpResultDto signUp(SignUpRequestDto dto);

  SignInResultDto signIn(SignInRequestDto dto) throws RuntimeException;
}
