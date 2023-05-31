package com.zerobase.moy.data.model.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@ApiModel(value = "로그인 결과 반환 DTO", description = "토큰DTO가 포함됩니다.")
public class SignInResultDto {
  @ApiModelProperty(notes = "토큰", example = "ATK(엑세스토큰),RTK(리프레쉬토큰)",required = true)
  private TokenDto token;

}
