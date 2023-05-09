package com.zerobase.moy.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LogoutResultDto extends SignUpResultDto {
  private boolean success;

  private int code;

  private String msg;
  private String expiredToken;
}
