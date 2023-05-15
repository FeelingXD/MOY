package com.zerobase.moy.data.model.sign;

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

  private String email;
  private String password;

  public static SignInRequestDto toDto(SignInForm form) {
    return SignInRequestDto.builder()
        .email(form.getEmail())
        .password(form.getPassword())
        .build();
  }
}
