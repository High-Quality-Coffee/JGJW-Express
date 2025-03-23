package com.zgzg.order.infrastructure.configuration;

import org.springframework.http.HttpStatus;

public class BusinessException extends Throwable {
	public BusinessException(HttpStatus httpStatus, String code, String message) {
	}
}
