package com.zerobase.moy.config;

import static com.zerobase.moy.config.ElasticSearchConfig.ELASTIC_DOMAIN_PACKAGE;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = {ELASTIC_DOMAIN_PACKAGE})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

  @Value("${elasticsearch.host}")
  private String host;
  @Value("${elasticsearch.index.diary}")
  private String diary_index;

  @Bean
  public String getDiaryIndex(){
    return diary_index;
  }


  static final String ELASTIC_DOMAIN_PACKAGE = "com.zerobase.moy.repository.elastic";

  @Override
  public RestHighLevelClient elasticsearchClient() {
    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(host)
        .build();
    return RestClients.create(clientConfiguration).rest();
  }
}
