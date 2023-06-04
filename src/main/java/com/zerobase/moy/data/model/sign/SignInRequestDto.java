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
@Builder
@Getter
@ApiModel(value = "로그인 요청 DTO", description = "로그인 리퀘스트되는 입니다.")
public class SignInRequestDto {

  @ApiModelProperty(value = "이메일")
  private String email;

  @ApiModelProperty(value = "비밀번호")
  private String password;

}
