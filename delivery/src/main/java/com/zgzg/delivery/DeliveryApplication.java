package com.zgzg.delivery;

import com.zgzg.common.aop.AopLogConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AopLogConfig.class)
public class DeliveryApplication {

  public static void main(String[] args) {
    SpringApplication.run(DeliveryApplication.class, args);
  }

}
