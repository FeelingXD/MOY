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
public class ReissueResultDto extends  SignUpResultDto{

  private TokenDto token;
  @Builder
  public ReissueResultDto(boolean success, int code, String msg, String atk,String rtk) {
    super(success, code, msg);
    this.token = TokenDto.builder()
        .atk(atk)
        .rtk(rtk)
        .build();
  }
}
