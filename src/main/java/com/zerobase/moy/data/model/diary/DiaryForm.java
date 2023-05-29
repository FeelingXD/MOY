package com.zerobase.moy.data.model.diary;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
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
public class DiaryForm {

  @Size(message = "제목은 50자 이하여야합니다.",max = 50)
  @NotBlank(message = "제목은 공백일수 없습니다.")
  private String title;
  @NotBlank(message = "내용은 공백일수 없습니다.")
  @Size(message = "최대 600자 까지 작성가능합니다.",max = 600)
  private String content;
  @NotBlank
  private String isPublic;

  public static DiaryRequestDto toDiaryRequestDto(DiaryForm form) {
    return DiaryRequestDto.builder()
        .title(form.getTitle())
        .content(form.getContent())
        .isPublic(!form.getIsPublic().isBlank())
        .build();
  }
}
