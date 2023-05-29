package com.zerobase.moy.data.model.sign;

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
  @NotBlank(message = "email은 공백일수 없습니다.")
  private String email;
  @NotBlank(message = "password는 공백일수 없습니다.")
  private String password;

  public static SignInRequestDto toDto(SignInForm form) {
    return SignInRequestDto.builder()
        .email(form.getEmail())
        .password(form.getPassword())
        .build();
  }
}
