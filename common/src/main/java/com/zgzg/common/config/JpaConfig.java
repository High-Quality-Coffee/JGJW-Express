package com.zgzg.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


import jakarta.persistence.EntityManager;

@Configuration
@EnableJpaAuditing
public class JpaConfig {

}
