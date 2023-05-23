package com.zerobase.moy.data.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setting(settingPath = "/static/elastic/diary-setting.json")
@Mapping(mappingPath = "/static/elastic/diary-mapping.json")
@Document(indexName = "#{@getDiaryIndex}")
public class DiaryDocument {

  @Id
  private Long id;
  @Field(type = FieldType.Long)
  private Long user_id;

  @Field(type = FieldType.Text)
  private String title;
  @Field(type = FieldType.Text)

  private String content;
  @Field(type = FieldType.Boolean, name = "is_public")
  private boolean isPublic;
  @Field(type = FieldType.Boolean)
  private boolean deleted;

  @Field(type = FieldType.Date, name = "create_date")
  private LocalDateTime createDate;
  @Field(type = FieldType.Date, name = "last_modified_date")

  private LocalDateTime lastModifiedDate;

}
