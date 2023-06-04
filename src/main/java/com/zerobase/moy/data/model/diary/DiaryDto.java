package com.zerobase.moy.data.model.diary;

import com.zerobase.moy.data.entity.Diary;
import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "다이어리-id")
  private Long id;
  @ApiModelProperty(value = "마지막 수정일")
  private LocalDateTime lastModifiedDate;
  @ApiModelProperty(value = "작성자(이메일)")
  private String author;
  @ApiModelProperty(value = "제목")
  private String title;
  @ApiModelProperty(value = "내용")
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
