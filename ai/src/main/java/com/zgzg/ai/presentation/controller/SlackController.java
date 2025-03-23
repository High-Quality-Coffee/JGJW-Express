package com.zgzg.ai.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.ai.application.dto.SendDirectMessageRequest;
import com.zgzg.ai.application.dto.VerifyMessageRequest;
import com.zgzg.ai.application.service.GeminiService;
import com.zgzg.ai.application.service.SlackService;
import com.zgzg.ai.presentation.DTO.GenerateMessageRequest;
import com.zgzg.ai.presentation.DTO.MessageResponseDTO;
import com.zgzg.ai.presentation.DTO.MessageUpdateDTO;
import com.zgzg.ai.presentation.DTO.PageableRequestDTO;
import com.zgzg.ai.presentation.DTO.PageableResponseDTO;
import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "AI & Slack", description = "메세지를 생성하고 slack으로 보내는 API")
@RestController
@RequestMapping("/api/v1/slack")
@RequiredArgsConstructor
public class SlackController {

	private final GeminiService geminiService;
	private final SlackService slackService;

	@PostMapping("/messages")
	public ResponseEntity<?> createMessage(@RequestBody GenerateMessageRequest requestDto) {
		String generatedMessage = geminiService.createMessage(requestDto);
		slackService.saveParsedMessage(generatedMessage);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.SLACK_SUCCESS, generatedMessage));
	}

	@GetMapping("/messages/{id}")
	@Secured({"ROLE_MASTER"})
	public ResponseEntity<?> getMessage(@RequestParam(name = "id") String id) {
		MessageResponseDTO message = slackService.getMessage(id);
		return ResponseEntity.ok().body(ApiResponseData.success(message));
	}

	@GetMapping("/messages")
	@Secured({"ROLE_MASTER"})
	public ResponseEntity<?> searchMessage(PageableRequestDTO pageableRequestDTO, @RequestParam(name = "keyword") String keyword) {
		PageableResponseDTO<MessageResponseDTO> message = slackService.searchMessage(pageableRequestDTO, keyword);
		return ResponseEntity.ok().body(ApiResponseData.success(message));
	}

	/**
	 * TODO : 미완성
	 * @param requestDto
	 * @return
	 */
	@PostMapping("/verify")
	@Secured({"ROLE_MASTER"})
	@Operation(summary = "생성된 메세지를 검증하는 API" ,description = "필수 기능 요구사항 충족 후 개발 예정")
	public ResponseEntity<?> verifyMessage(@RequestBody VerifyMessageRequest requestDto) {
		geminiService.verifyMessage(requestDto);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.GEMINI_VERIFY_SUCCESS));
	}

	@PostMapping("/messages/send")
	@Operation(summary = "send message using slack api" ,description = "DM")
	public ResponseEntity<?> sendDirectMessage(@RequestBody SendDirectMessageRequest requestDto) {
		slackService.sendDirectMessage(requestDto);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.SLACK_SUCCESS));
	}

	@PutMapping("/messages/{id}")
	@Secured({"ROLE_MASTER"})
	public ResponseEntity<?> updateMessage(@PathVariable(name = "id") String id,
		@RequestBody MessageUpdateDTO requestDto) {
		slackService.updateMessage(id, requestDto);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.SLACK_SUCCESS));
	}

	@PatchMapping("message")
	@Secured({"ROLE_MASTER"})
	public ResponseEntity<?> deleteMessage(@RequestParam("id") String id) {
		slackService.deleteMessage(id);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.SLACK_MASSAGE_DELETE_SUCCESS));
	}

}
