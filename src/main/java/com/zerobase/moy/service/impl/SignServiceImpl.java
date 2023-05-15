package com.zerobase.moy.service.impl;

import com.zerobase.moy.config.security.JwtTokenProvider;
import com.zerobase.moy.data.entity.User;
import com.zerobase.moy.data.model.sign.LogoutResultDto;
import com.zerobase.moy.data.model.sign.ReissueResultDto;
import com.zerobase.moy.data.model.sign.SignInRequestDto;
import com.zerobase.moy.data.model.sign.SignInResultDto;
import com.zerobase.moy.data.model.sign.SignUpRequestDto;
import com.zerobase.moy.data.model.sign.TokenDto;
import com.zerobase.moy.response.exception.CustomException;
import com.zerobase.moy.response.exception.ErrorCode;
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

  private final String REDIS_PRE_FIX = "RT:";
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final PasswordEncoder passwordEncoder;
  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public void signUp(SignUpRequestDto dto) {

    if (userRepository.countByEmail(dto.getEmail()) > 0) {
      throw new CustomException(ErrorCode.EMAIL_ALREADY_EXIST);
    }

    User savedUser = userRepository.save(
        User.builder()
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(Collections.singletonList("ROLE_USER"))
            .build());

  }

  @Override
  public SignInResultDto signIn(SignInRequestDto dto) {
    User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
    }
    var atk = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles());
    var rtk = jwtTokenProvider.createRefreshToken();

    var result = SignInResultDto.builder()
        .token(TokenDto.builder()
            .atk(atk)
            .rtk(rtk)
            .build())
        .build();

    redisTemplate.opsForValue()
        .set(REDIS_PRE_FIX + user.getUsername(), rtk, jwtTokenProvider.getExpiration(atk),
            TimeUnit.MILLISECONDS);

    return result;
  }

  @Override
  public LogoutResultDto logout(HttpServletRequest req) {
    var atk = jwtTokenProvider.resolveAtk(req);

    if (!jwtTokenProvider.validateToken(atk)) {
      throw new CustomException(ErrorCode.INVALID_ATK);
    }

    var user = jwtTokenProvider.getAuthentication(atk);

    if (redisTemplate.opsForValue().get(REDIS_PRE_FIX + user.getName()) != null) {
      redisTemplate.delete(REDIS_PRE_FIX + user.getName());
    }

    var expiration = jwtTokenProvider.getExpiration(atk);
    redisTemplate.opsForValue().set(atk, "logout", expiration, TimeUnit.MILLISECONDS);

    return LogoutResultDto.builder()
        .expiredToken(atk)
        .build();
  }

  @Override
  public ReissueResultDto reissue(HttpServletRequest req) {
    var atk = jwtTokenProvider.resolveAtk(req);
    var rtk = jwtTokenProvider.resolveRtk(req);

    if (!jwtTokenProvider.validateToken(rtk)) {
      throw new CustomException(ErrorCode.INVALID_RTK);
    }

    var user = (User) jwtTokenProvider.getAuthentication(atk).getPrincipal();

    return ReissueResultDto.builder()
        .token(TokenDto.builder()
            .atk(jwtTokenProvider.createAccessToken(user.getEmail(), user.getRoles()))
            .rtk(rtk)
            .build())
        .build();
  }


}
