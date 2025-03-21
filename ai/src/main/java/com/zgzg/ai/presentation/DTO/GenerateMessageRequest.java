package com.zgzg.ai.presentation.DTO;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

@Getter
public class GenerateMessageRequest {
	@Schema(example = "12345")
	private String orderId;
	@Schema(example = "책")
	private String product;
	@Schema(example = "3")
	private Integer quantity;
	@Schema(example = "4월 1일 전에 보내주세요")
	private String request;
	@Schema(example = "경기 북부 센터")
	private String originHub;
	@Schema(example = "부산 센터")
	private String destinationHub;
	@Schema(example = "[\"대전 센터\"]")
	private List<String> intermediateHubs;
	@Schema(example = "09:00-18:00")
	private String workHours;

}
