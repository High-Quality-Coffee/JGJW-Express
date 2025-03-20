package com.zgzg.ai.application.dto;

import lombok.Getter;

@Getter
public class VerifyMessageRequest {
	private String message;
	private String historicalDelaysJson;
}
