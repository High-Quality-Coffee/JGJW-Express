package com.zgzg.hub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class PageConfig {

  @Bean
  public PageableHandlerMethodArgumentResolverCustomizer customPageResolver() {
    return resolver -> {
      resolver.setOneIndexedParameters(true); // page = 1부터 시작 보정
      resolver.setFallbackPageable(PageRequest.of(0, 10)); // 0번째 페이지, 개수 10
    };
  }
}
