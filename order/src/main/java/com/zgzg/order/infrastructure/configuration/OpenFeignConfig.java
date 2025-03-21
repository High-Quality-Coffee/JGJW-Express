package com.zgzg.order.infrastructure.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zgzg.order.infrastructure.util.FeignClientInterceptor;

@Configuration
@EnableFeignClients("com.zgzg.order")
public class OpenFeignConfig {

	@Bean
	public FeignClientInterceptor feignClientInterceptor() {
		return new FeignClientInterceptor();
	}
}