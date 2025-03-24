package com.zgzg.ai;

import com.zgzg.common.aop.AopLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.zgzg.common.security", "com.zgzg.ai"})
@Import(AopLogConfig.class)
public class AiApplication {

  public static void main(String[] args) {
    SpringApplication.run(AiApplication.class, args);
  }

}
