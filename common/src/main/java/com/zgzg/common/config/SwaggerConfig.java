package com.zgzg.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@ConditionalOnClass(name = {
	"io.swagger.v3.oas.models.OpenAPI",
	"org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer"
})
public class SwaggerConfig {
	private static final String SECURITY_SCHEME_NAME = "JWT";

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.openapi("3.0.3")
			.info(apiInfo())
			.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
			.components(securityComponents());
	}

	private Info apiInfo() {
		return new Info()
			.title("ZgZg API")
			.version("1.0.0")
			.description("ZgZg API 문서입니다.")
			.contact(new Contact()
					.name("ZgZg")
				//.email("contact@ZgZg.org")
				//.url("https://www.ZgZg.org")
			);
	}

	private Components securityComponents() {
		return new Components()
			.addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT"));
	}
}
