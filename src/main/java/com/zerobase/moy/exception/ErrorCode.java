package com.zerobase.moy.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  //Sign
  USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "사용자를 찾을수 없습니다."),
  INVALID_RTK(HttpStatus.BAD_REQUEST, "유효하지않은 Refresh토큰 입니다."),
  INVALID_ATK(HttpStatus.BAD_REQUEST, "유효하지않은 Access토큰 입니다."),
  EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다."),
  PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지않습니다."),

  // DIARY
  DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 다이어리 입니다."),
  NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
  DIARY_IS_NOT_PUBLIC(HttpStatus.BAD_REQUEST, "비공개 다이어리 입니다.");

  private final HttpStatus status;
  private final String msg;
}