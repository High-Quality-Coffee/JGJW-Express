package com.zgzg.delivery.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Logger;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableFeignClients("com.zgzg.delivery")
@Slf4j
public class OpenFeignConfig {

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public FeignClientErrorDecoder feignErrorDecoder() {
		return new FeignClientErrorDecoder(objectMapper);
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
}
