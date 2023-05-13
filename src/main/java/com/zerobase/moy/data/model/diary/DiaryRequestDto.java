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
public class DiaryRequestDto {

  private String title;
  private String content;
  private boolean isPublic;

  public static Diary toEntity(DiaryRequestDto dto) {
    return Diary.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .isPublic(dto.isPublic)
        .build();
  }
}
