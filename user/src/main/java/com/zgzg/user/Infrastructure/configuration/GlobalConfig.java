package com.zgzg.user.Infrastructure.configuration;

import com.zgzg.common.config.JpaConfig;
import com.zgzg.common.config.PropertyConfig;
import com.zgzg.common.security.GlobalSecurityConfig;
import com.zgzg.common.security.GlobalSecurityContextFilter;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(basePackages = {
        "com.zgzg.user"
})
@Import({
        JpaConfig.class,
        PropertyConfig.class,
        GlobalSecurityContextFilter.class
})
public class GlobalConfig {
}



