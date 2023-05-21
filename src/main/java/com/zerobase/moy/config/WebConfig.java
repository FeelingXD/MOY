package com.zerobase.moy.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

  @Value("${CLOVA.url}")
  String END_POINT;
  @Value("${CLOVA.header.client-id}")
  String CLOVA_HEADER_ID;
  @Value("${CLOVA.header.client-secret}")
  String CLOVA_HEADER_KEY;

  String CLOVA_CONTENT_TYPE = "Content-Type";

  @Value("${CLOVA.client.id}")
  String API_ID;
  @Value("${CLOVA.client.secret}")
  String API_SECRET_KEY;

  @Bean
  public WebClient ClovaWebClient() {
    return WebClient.builder()
        .baseUrl(END_POINT)
        .defaultHeader(CLOVA_HEADER_ID, API_ID)
        .defaultHeader(CLOVA_HEADER_KEY, API_SECRET_KEY)
        .defaultHeader(CLOVA_CONTENT_TYPE, "application/json")
        .build();
  }
}
