package com.zerobase.moy.service.impl;

import com.zerobase.moy.common.CommonResponse;
import com.zerobase.moy.config.Security.JwtTokenProvider;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.SignInRequestDto;
import com.zerobase.moy.data.model.SignInResultDto;
import com.zerobase.moy.data.model.SignUpRequestDto;
import com.zerobase.moy.data.model.SignUpResultDto;
import com.zerobase.moy.repository.UserRepository;
import com.zerobase.moy.service.SignService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;


  @Override
  public SignUpResultDto signUp(SignUpRequestDto dto) {

    User savedUser = userRepository.save(
        User.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(Collections.singletonList("ROLE_USER"))
            .build());

    SignUpResultDto result = new SignInResultDto();

    if (!savedUser.getUsername().isEmpty()) {
      setSuccessResult(result);
    } else {
      setFailResult(result);
    }

    return result;
  }

  @Override
  public SignInResultDto signIn(SignInRequestDto dto) throws RuntimeException {
    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new RuntimeException("사용자 정보없음"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      log.info(dto.getPassword());
      log.info(passwordEncoder.encode(dto.getPassword()));
      log.info(user.getPassword());
      throw new RuntimeException("패스워드 불일치 "); //todo: custom Exception 처리할것
    }

    var result = SignInResultDto.builder()
        .token(jwtTokenProvider.createToken(user.getUsername(), user.getRoles()))
        .build();
    setSuccessResult(result);

    return result;
  }

  private void setSuccessResult(SignUpResultDto result) {
    result.setSuccess(true);
    result.setCode(CommonResponse.SUCCESS.getCode());
    result.setMsg(CommonResponse.SUCCESS.getMsg());
  }

  // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
  private void setFailResult(SignUpResultDto result) {
    result.setSuccess(false);
    result.setCode(CommonResponse.FAIL.getCode());
    result.setMsg(CommonResponse.FAIL.getMsg());
  }
}
