package com.zgzg.ai.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.ai.application.service.GeminiService;
import com.zgzg.common.response.ApiResponseData;

import lombok.RequiredArgsConstructor;

@RestController("/api/v1/gemini")
@RequiredArgsConstructor
public class GeminiController {

	private final GeminiService geminiService;

	@PostMapping("/")
	public ResponseEntity<?> createMessage() {
		geminiService.createMessage();
		return ResponseEntity.ok().body(ApiResponseData.success("test"));
	}


}
