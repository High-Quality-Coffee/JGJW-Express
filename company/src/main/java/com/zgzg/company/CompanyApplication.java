package com.zgzg.company;

import com.zgzg.common.aop.AopLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients
@SpringBootApplication
@Import(AopLogConfig.class)
public class CompanyApplication {

  public static void main(String[] args) {
    SpringApplication.run(CompanyApplication.class, args);
  }

}
