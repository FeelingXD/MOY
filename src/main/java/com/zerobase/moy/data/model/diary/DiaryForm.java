package com.zerobase.moy.data.model.diary;

import com.zerobase.moy.data.entity.Diary;
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

  public static Diary toEntity(DiaryForm form){
    return Diary.builder()
        .title(form.getTitle())
        .content(form.getContent())
        .isPublic(!form.getIsPublic().isBlank())
        .build();
  }
}
