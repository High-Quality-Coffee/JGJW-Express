package com.zgzg.ai.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgzg.ai.application.dto.SendDirectMessageRequest;
import com.zgzg.ai.application.dto.VerifyMessageRequest;
import com.zgzg.ai.domain.VerificationResult;
import com.zgzg.ai.domain.persistence.GeminiRepository;
import com.zgzg.ai.domain.persistence.SlackRepository;
import com.zgzg.ai.presentation.DTO.GenerateMessageRequest;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

	private final GeminiRepository geminiRepository;
	private final SlackRepository slackRepository;
	private final ObjectMapper objectMapper;

	public String createMessage(GenerateMessageRequest requestDto) {

		// prompt
		StringBuilder prompt = new StringBuilder();
		prompt.append("배송 정보를 기반으로 Slack 메시지 초안을 생성해줘. ");
		prompt.append("주문번호: ").append(requestDto.getOrderId()).append(", ");
		prompt.append("출발: ").append(requestDto.getOriginHub()).append(", ");
		prompt.append("도착: ").append(requestDto.getDestinationHub()).append(", ");
		prompt.append("경유지: ");
		for (int i = 0; i < requestDto.getIntermediateHubs().size(); i++) {
			prompt.append(requestDto.getIntermediateHubs().get(i));
			if (i < requestDto.getIntermediateHubs().size() - 1) {
				prompt.append(", ");
			}
		}
		prompt.append("예상 소요시간: ").append(requestDto.getWorkHours()).append(".");

		// Gemini call payload
		Map<String, Object> messageTurn = new HashMap<>();
		messageTurn.put("role", "user");
		Map<String, String> textPart = new HashMap<>();
		textPart.put("text", prompt.toString());
		List<Map<String, String>> parts = new ArrayList<>();
		parts.add(textPart);
		messageTurn.put("parts", parts);
		List<Map<String, Object>> contents = new ArrayList<>();
		contents.add(messageTurn);
		Map<String, Object> payloadMap = new HashMap<>();
		payloadMap.put("contents", contents);
		String payload = null;
		try {
			payload = objectMapper.writeValueAsString(payloadMap);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}

		// Call Gemini API
		String response = null;
		try {
			response = geminiRepository.callGeminiApi(payload);
		} catch (Exception e) {
			throw new BaseException(e);
		}

		// error check
		String errorMessage = null;
		try {
			errorMessage = geminiRepository.parseError(response);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		if (errorMessage != null) {
			return "Gemini API 오류: " + errorMessage;
		}

		String generatedMessage = null;
		try {
			generatedMessage = geminiRepository.parseGeneratedMessage(response);
		} catch (Exception e) {
			throw new BaseException(e);
		}
		return generatedMessage;
	}

	public VerificationResult verifyMessage(VerifyMessageRequest requestDto) {


		// 1) 검증 요청용 프롬프트
		String prompt = "다음 Slack 메시지를 검증해줘: " + requestDto.getHistoricalDelaysJson() +
			". 과거 배송 지연 데이터: " + requestDto.getHistoricalDelaysJson() + ".";

		Map<String, Object> messageTurn = new HashMap<>();
		messageTurn.put("role", "user");

		Map<String, String> textPart = new HashMap<>();
		textPart.put("text", prompt);

		List<Map<String, String>> parts = new ArrayList<>();
		parts.add(textPart);
		messageTurn.put("parts", parts);

		List<Map<String, Object>> contents = new ArrayList<>();
		contents.add(messageTurn);

		Map<String, Object> payloadMap = new HashMap<>();
		payloadMap.put("contents", contents);

		String payload = null;
		try {
			payload = objectMapper.writeValueAsString(payloadMap);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}

		// 2) Gemini API 호출
		String response = null;
		try {
			response = geminiRepository.callGeminiApi(payload);
		} catch (Exception e) {
			throw new BaseException(e);
		}

		// 3) 오류 체크
		String errorMessage = null;
		try {
			errorMessage = geminiRepository.parseError(response);
		} catch (Exception e) {
			throw new BaseException(e);
		}

		// 4) 검증 후 수정된 메시지(후보) 파싱
		String verifiedMsg = null;
		try {
			verifiedMsg = geminiRepository.parseVerificationMessage(response);
		} catch (Exception e) {
			throw new BaseException(e);
		}

		if (errorMessage != null) {
			VerificationResult result = new VerificationResult(!verifiedMsg.isEmpty(), " ",verifiedMsg);
			return result;
		} else return new VerificationResult(!verifiedMsg.isEmpty(), errorMessage ,verifiedMsg);
	}

}
