package com.zerobase.moy.data.model.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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
@ApiModel(value = "회원가입DTO", description = "회원가입 요청의 Domain class 입니다. 이메일 ,비밀번호, 이름이 포함됩니다.")
public class SignUpRequestDto{

  @ApiModelProperty(value = "이메일")
  private String email;

  @ApiModelProperty(value = "비밀번호")
  private String password;

  @ApiModelProperty(value = "이름")
  private String name;

}
