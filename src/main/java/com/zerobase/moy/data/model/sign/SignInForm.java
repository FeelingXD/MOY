package com.zerobase.moy.data.model.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
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
public class SignInForm {
  @ApiModelProperty(notes = "이메일", example = "example@example.com",required = true)
  @NotBlank(message = "email은 공백일수 없습니다.")
  private String email;
  @ApiModelProperty(notes = "비밀번호", example = "myPassword",required = true)
  @NotBlank(message = "password는 공백일수 없습니다.")
  private String password;

  public static SignInRequestDto toDto(SignInForm form) {
    return SignInRequestDto.builder()
        .email(form.getEmail())
        .password(form.getPassword())
        .build();
  }
}
