package com.zgzg.gateway.infrastructure.configuration;

import com.zgzg.common.config.JpaConfig;
import com.zgzg.common.config.PropertyConfig;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PropertyConfig.class
})
@ImportAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        ReactiveSecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class,
        ReactiveManagementWebSecurityAutoConfiguration.class
})
public class GlobalConfig {
}
