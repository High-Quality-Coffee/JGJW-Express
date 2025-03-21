package com.zgzg.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.zgzg.common.config.JpaConfig;
import com.zgzg.common.config.PropertyConfig;
import com.zgzg.common.security.GlobalSecurityConfig;
import com.zgzg.common.security.GlobalSecurityContextFilter;

@Configuration
@Import({
	JpaConfig.class,
	PropertyConfig.class,
	GlobalSecurityConfig.class,
	GlobalSecurityContextFilter.class
})
public class GlobalConfig {
}
