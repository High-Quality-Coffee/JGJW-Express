package com.zgzg.order.infrastructure.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignClientInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
			&& authentication.getCredentials() instanceof String token) {
			requestTemplate.header("access", "Bearer " + token);
		}

	}
}
