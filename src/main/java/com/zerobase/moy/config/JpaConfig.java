package com.zerobase.moy.config;

import static com.zerobase.moy.config.JpaConfig.RDS_PACKAGE;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@Configuration
@EnableJpaRepositories(basePackages = {RDS_PACKAGE})
public class JpaConfig {

  static final String RDS_PACKAGE = "com.zerobase.moy.repository.jpa";
}
