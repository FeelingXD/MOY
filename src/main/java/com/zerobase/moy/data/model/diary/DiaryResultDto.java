package com.zerobase.moy.data.model.diary;

import com.zerobase.moy.data.entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class DiaryResultDto {


  private String title;
  private String content;


  public static DiaryResultDto of(Diary diary) {
    return DiaryResultDto.builder()
        .title(diary.getTitle())
        .content(diary.getContent())
        .build();
  }
}
