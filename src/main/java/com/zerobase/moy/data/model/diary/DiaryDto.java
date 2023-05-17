package com.zerobase.moy.data.model.diary;

import com.zerobase.moy.data.entity.Diary;
import java.time.LocalDateTime;
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
public class DiaryDto {

  private Long id;
  private LocalDateTime lastModifiedDate;
  private String author;
  private String title;
  private String contents;

  public static DiaryDto of(Diary d) {
    return DiaryDto.builder()
        .id(d.getId())
        .author(d.getUser().getUsername())
        .title(d.getTitle())
        .contents(d.getContent())
        .lastModifiedDate(d.getLastModifiedDate())
        .build();
  }
}
