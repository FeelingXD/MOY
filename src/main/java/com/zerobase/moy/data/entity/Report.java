package com.zerobase.moy.data.entity;

import com.zerobase.moy.data.converter.ReportJsonConverter;
import com.zerobase.moy.data.model.CLOVA.SentimentResponse;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

  @Id
  private Long id;

  @OneToOne
  @MapsId
  private Diary diary;


  @Convert(converter = ReportJsonConverter.class)
  @Column(columnDefinition = "TEXT")
  private SentimentResponse json;


}
