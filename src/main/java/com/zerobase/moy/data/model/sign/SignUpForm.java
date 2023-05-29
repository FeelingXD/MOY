package com.zerobase.moy.data.model.sign;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
  @Email(message = "이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일은 공백일 수 없습니다.")
  private String email;
  @Size(message = " 비밀번호는 4자 이상 10자 이하여야합니다.",max = 10 ,min = 4)
  private String password;
  @NotBlank(message = "이름은 공백일 수 없습니다.")
  private String name;

  public static SignUpRequestDto toDto(SignUpForm form) {
    return SignUpRequestDto.builder()
        .email(form.getEmail())
        .password(form.getPassword())
        .name(form.getName())
        .build();
  }
}
