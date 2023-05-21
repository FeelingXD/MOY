package com.zerobase.moy.data.model.CLOVA;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SentimentResponse {
  private Document document;
  private List<Sentence> sentences;

  @Getter
  @Setter
  @ToString
  public static class Document {
    private String sentiment;
    private Confidence confidence;

  }
  @Getter
  @Setter
  @ToString
  public static class Sentence {
    private String content;
    private int offset;
    private int length;
    private String sentiment;
    private Confidence confidence;
    private List<Highlight> highlights;

  }
  @Getter
  @Setter
  @ToString
  public static class Confidence {
    private double neutral;
    private double positive;
    private double negative;

  }
  @Getter
  @Setter
  @ToString
  public static class Highlight {
    private int offset;
    private int length;

  }
}