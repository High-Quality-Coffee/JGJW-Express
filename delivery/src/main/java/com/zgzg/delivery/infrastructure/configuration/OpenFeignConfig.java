package com.zgzg.delivery.infrastructure.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableFeignClients("com.zgzg.delivery")
@Slf4j
public class OpenFeignConfig {

}
