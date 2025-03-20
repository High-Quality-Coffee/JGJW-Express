package com.zgzg.hub.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverDirectionConfig {

  @Value("${naver_api_key_id}")
  private String apikeyId;

  @Value("${naver_api_key}")
  private String apiKey;

  @Bean
  public RequestInterceptor naverRequestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("x-ncp-apigw-api-key-id", apikeyId);
      requestTemplate.header("x-ncp-apigw-api-key", apiKey);
    };
  }
}
