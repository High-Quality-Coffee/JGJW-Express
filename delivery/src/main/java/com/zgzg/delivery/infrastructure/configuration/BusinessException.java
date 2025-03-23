package com.zgzg.delivery.infrastructure.configuration;

import org.springframework.http.HttpStatus;

public class BusinessException extends Throwable {
	public BusinessException(HttpStatus httpStatus, String code, String message) {
	}
}
