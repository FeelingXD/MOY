package com.zerobase.moy.service.impl;

import com.zerobase.moy.common.CommonResponse;
import com.zerobase.moy.config.security.JwtTokenProvider;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.LogoutResultDto;
import com.zerobase.moy.data.model.ReissueResultDto;
import com.zerobase.moy.data.model.SignInRequestDto;
import com.zerobase.moy.data.model.SignInResultDto;
import com.zerobase.moy.data.model.SignUpRequestDto;
import com.zerobase.moy.data.model.SignUpResultDto;
import com.zerobase.moy.repository.UserRepository;
import com.zerobase.moy.service.SignService;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final RedisTemplate<String, String> redisTemplate;

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
      throw new RuntimeException("패스워드 불일치 ");
    }
    var atk = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
    var rtk = jwtTokenProvider.createRefreshToken();

    var result = SignInResultDto.builder()
        .atk(atk)
        .rtk(rtk)
        .build();
    setSuccessResult(result);

    redisTemplate.opsForValue()
        .set("RT:" + user.getUsername(), rtk, jwtTokenProvider.getExpiration(atk),
            TimeUnit.MILLISECONDS);

    return result;
  }

  @Override
  public LogoutResultDto logout(HttpServletRequest req) {
    var atk = jwtTokenProvider.resolveAtk(req);
    var rtk = jwtTokenProvider.resolveRtk(req);

    if (!jwtTokenProvider.validateToken(atk)) {
      throw new IllegalArgumentException("로그아웃 : 유효하지않은 토큰");
    }

    var user = jwtTokenProvider.getAuthentication(atk);

    if (redisTemplate.opsForValue().get("RT:" + user.getName()) != null) {
      redisTemplate.delete("RT:" + user.getName());
    }

    var expiration = jwtTokenProvider.getExpiration(atk);
    redisTemplate.opsForValue().set(atk, "logout", expiration, TimeUnit.MILLISECONDS);

    var result = LogoutResultDto.builder()
        .expiredToken(atk)
        .build();
    setSuccessResult(result);

    return result;
  }

  @Override
  public ReissueResultDto reissue(HttpServletRequest req) {
    var atk = jwtTokenProvider.resolveAtk(req);
    var rtk = jwtTokenProvider.resolveRtk(req);

    if (!jwtTokenProvider.validateToken(rtk)) {
      throw new RuntimeException("유효하지 않은 refresh token 입니다.");
    }
    var user = (User) jwtTokenProvider.getAuthentication(atk).getPrincipal();

    var result = ReissueResultDto.builder()
        .atk(jwtTokenProvider.createAccessToken(user.getEmail(), user.getRoles()))
        .rtk(rtk)
        .build();

    return result;
  }

  private void setSuccessResult(SignUpResultDto result) {
    result.setSuccess(true);
    result.setCode(CommonResponse.SUCCESS.getCode());
    result.setMsg(CommonResponse.SUCCESS.getMsg());
  }

  private void setFailResult(SignUpResultDto result) {
    result.setSuccess(false);
    result.setCode(CommonResponse.FAIL.getCode());
    result.setMsg(CommonResponse.FAIL.getMsg());
  }
}
