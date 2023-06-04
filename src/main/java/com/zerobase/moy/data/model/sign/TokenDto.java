package com.zerobase.moy.data.model.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
@ApiModel(value = "토큰Dto", description = "엑세스 토큰과 리프레쉬 토큰 DTO입니다.")
public class TokenDto {

  @ApiModelProperty(value = "엑세스 토큰")
  private String atk;

  @ApiModelProperty(value = "리프레쉬 토큰")
  private String rtk;

}
