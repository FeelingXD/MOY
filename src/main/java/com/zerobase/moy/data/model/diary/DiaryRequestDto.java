package com.zerobase.moy.data.model.diary;

import com.zerobase.moy.data.entity.Diary;
import io.swagger.annotations.ApiModelProperty;
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

  @ApiModelProperty(value = "제목")
  private String title;

  @ApiModelProperty(value = "내용")
  private String content;

  @ApiModelProperty(value = "공개여부")
  private boolean isPublic;

  public static Diary toEntity(DiaryRequestDto dto) {
    return Diary.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .isPublic(dto.isPublic)
        .build();
  }
}
