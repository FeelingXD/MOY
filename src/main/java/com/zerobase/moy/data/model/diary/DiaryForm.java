package com.zerobase.moy.data.model.diary;

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

  private String title;
  private String content;
  private String isPublic;

  public static DiaryRequestDto toDiaryRequestDto(DiaryForm form) {
    return DiaryRequestDto.builder()
        .title(form.getTitle())
        .content(form.getContent())
        .isPublic(!form.getIsPublic().isBlank())
        .build();
  }
}
