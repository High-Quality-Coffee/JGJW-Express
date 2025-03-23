package com.zgzg.order.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Logger;

@Configuration
@EnableFeignClients("com.zgzg.order")
public class OpenFeignConfig {

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public FeignClientErrorDecoder feignErrorDecoder() {
		return new FeignClientErrorDecoder(objectMapper);
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL; // NONE, BASIC, HEADERS, FULL 중 선택
	}
}