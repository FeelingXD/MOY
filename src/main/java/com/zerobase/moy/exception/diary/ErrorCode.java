package com.zerobase.moy.exception.diary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  //


  // DIARY
  DIARY_NOT_FOUND(HttpStatus.NOT_FOUND,"없는 다이어리 입니다."),
  NOT_AUTHORIZED(HttpStatus.FORBIDDEN,"권한이 없습니다."),
  DIARY_IS_NOT_PUBLIC(HttpStatus.BAD_REQUEST,"비공개 다이어리 입니다.");

  private final HttpStatus status;
  private final String msg;
  }