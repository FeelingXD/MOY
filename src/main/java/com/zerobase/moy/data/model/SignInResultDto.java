package com.zerobase.moy.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter

public class SignInResultDto extends SignUpResultDto {

  private TokenDto token;

  @Builder
  public SignInResultDto(boolean success, int code, String msg, String atk, String rtk) {
    super(success, code, msg);
    this.token = TokenDto.builder()
        .atk(atk)
        .rtk(rtk)
        .build();
  }
}
