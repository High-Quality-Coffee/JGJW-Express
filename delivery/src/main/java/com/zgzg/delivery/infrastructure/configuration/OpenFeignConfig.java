package com.zgzg.delivery.infrastructure.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.zgzg.delivery")
public class OpenFeignConfig {
}
