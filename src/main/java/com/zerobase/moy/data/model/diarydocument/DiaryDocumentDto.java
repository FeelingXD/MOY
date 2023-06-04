package com.zerobase.moy.data.model.diarydocument;

import com.zerobase.moy.data.domain.DiaryDocument;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "다이어리 문서 DTO(ElasticEntity)")
public class DiaryDocumentDto {
  @ApiModelProperty(value = "문서 id")
  private Long id;

  @ApiModelProperty(value = "제목")
  private String title;

  @ApiModelProperty(value = "내용(컨텐츠)")
  private String content;
  @ApiModelProperty(value = "생성 일자")
  private LocalDateTime createDate;
  @ApiModelProperty(value = "최근 수정일자")
  private LocalDateTime lastModifiedDate;

  public static DiaryDocumentDto of(DiaryDocument d) {
    return DiaryDocumentDto.builder()
        .id(d.getId())
        .title(d.getTitle())
        .content(d.getContent())
        .createDate(d.getCreateDate())
        .lastModifiedDate(d.getLastModifiedDate())
        .build();
  }
}
