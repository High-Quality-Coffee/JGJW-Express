package com.zgzg.ai.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SendDirectMessageRequest {
	@Schema(example = "U087R317SMN")
	private String userId;
	private String text;
}
