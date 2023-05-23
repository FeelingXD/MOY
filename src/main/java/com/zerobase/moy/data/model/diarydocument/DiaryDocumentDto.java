package com.zerobase.moy.data.model.diarydocument;

import com.zerobase.moy.data.domain.DiaryDocument;
import com.zerobase.moy.data.model.diary.DiaryRequestDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
public class DiaryDocumentDto {

  private Long id;
  private String title;
  private String content;

  private LocalDateTime createDate;

  private LocalDateTime lastModifiedDate;

  public static DiaryDocumentDto of(DiaryDocument d){
    return DiaryDocumentDto.builder()
        .id(d.getId())
        .title(d.getTitle())
        .content(d.getContent())
        .createDate(d.getCreateDate())
        .lastModifiedDate(d.getLastModifiedDate())
        .build();
  }
}
