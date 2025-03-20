package com.zgzg.ai.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.ai.application.dto.SendDirectMessageRequest;
import com.zgzg.ai.application.dto.VerifyMessageRequest;
import com.zgzg.ai.application.service.GeminiService;
import com.zgzg.ai.domain.VerificationResult;
import com.zgzg.ai.presentation.DTO.GenerateMessageRequest;
import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import lombok.RequiredArgsConstructor;

@RestController("/api/v1/gemini")
@RequiredArgsConstructor
public class GeminiController {

	private final GeminiService geminiService;

	@PostMapping("/")
	public ResponseEntity<?> createMessage(@RequestBody GenerateMessageRequest requestDto) {
		geminiService.createMessage(requestDto);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.SLACK_SUCCESS));
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyMessage(@RequestBody VerifyMessageRequest requestDto){
		geminiService.verifyMessage(requestDto);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.GEMINI_VERIFY_SUCCESS));
	}

	@PostMapping("/send")
	public ResponseEntity<?> sendDirectMessage(@RequestBody SendDirectMessageRequest requestDto) {
		geminiService.sendDirectMessage(requestDto);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.SLACK_SUCCESS));
	}

}
