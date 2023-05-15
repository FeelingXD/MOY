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
public class SignUpForm {

  private String email;
  private String password;
  private String name;

  public static SignUpRequestDto toDto(SignUpForm form) {
    return SignUpRequestDto.builder()
        .email(form.getEmail())
        .password(form.getPassword())
        .name(form.getName())
        .build();
  }
}
