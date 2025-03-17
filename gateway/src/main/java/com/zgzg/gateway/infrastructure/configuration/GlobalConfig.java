package com.zgzg.gateway.infrastructure.configuration;

import com.zgzg.common.config.JpaConfig;
import com.zgzg.common.config.PropertyConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        PropertyConfig.class
})
public class GlobalConfig {
}
