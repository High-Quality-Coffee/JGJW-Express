package com.zgzg.hub;

import com.zgzg.common.aop.AopLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import(AopLogConfig.class)
public class HubApplication {

  public static void main(String[] args) {
    SpringApplication.run(HubApplication.class, args);
  }

}
