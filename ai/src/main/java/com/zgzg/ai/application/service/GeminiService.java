package com.zgzg.ai.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgzg.ai.application.dto.VerifyMessageRequest;
import com.zgzg.ai.domain.VerificationResult;
import com.zgzg.ai.domain.persistence.GeminiRepository;
import com.zgzg.ai.domain.persistence.SlackRepository;
import com.zgzg.ai.presentation.DTO.GenerateMessageRequest;
import com.zgzg.common.exception.BaseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeminiService {

	private final GeminiRepository geminiRepository;
	private final SlackRepository slackRepository;
	private final ObjectMapper objectMapper;

	public String createMessage(GenerateMessageRequest requestDto) {

		/**
		 * TODO: requestDto.getOrderId()활용으로 변경
		 */
		String startdate = "2025-03-22";
		;

		// prompt
		StringBuilder prompt = new StringBuilder();
		prompt.append("배송 정보를 기반으로 Slack 메시지 초안을 생성해줘. 입력받은 정보를 토대로 최종발송기한(finalDeliveryStratTime)을 계산해 줘");
		prompt.append("주문번호: ").append(requestDto.getOrderId()).append(", ");
		prompt.append("배송 출발: ").append(startdate).append(", ");
		prompt.append("상품: ").append(requestDto.getProduct()).append(", ");
		prompt.append("수량: ").append(requestDto.getQuantity()).append(", ");
		prompt.append("요청사항: ").append(requestDto.getRequest()).append(", ");
		prompt.append("출발: ").append(requestDto.getOriginHub()).append(", ");
		prompt.append("도착: ").append(requestDto.getDestinationHub()).append(", ");
		prompt.append("경유지: ");
		for (int i = 0; i < requestDto.getIntermediateHubs().size(); i++) {
			prompt.append(requestDto.getIntermediateHubs().get(i));
			if (i < requestDto.getIntermediateHubs().size() - 1) {
				prompt.append(", ");
			}
		}
		prompt.append("근무시간: ").append(requestDto.getWorkHours()).append(".");

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

		// 3. generationConfig에 JSON 스키마 추가 (응답 형식 일관성 확보)
		Map<String, Object> generationConfig = new HashMap<>();
		generationConfig.put("response_mime_type", "application/json");

		// JSON 스키마 정의 (속성 순서를 위해 LinkedHashMap 사용)
		Map<String, Object> responseSchema = new LinkedHashMap<>();
		responseSchema.put("type", "object");

		Map<String, Object> properties = new LinkedHashMap<>();
		properties.put("messageContent", Map.of("type", "string"));
		properties.put("messageTitle", Map.of("type", "string"));
		properties.put("orderStartDate", Map.of("type", "string"));
		properties.put("orderNumber", Map.of("type", "string"));
		properties.put("originHub", Map.of("type", "string"));
		properties.put("destinationHub", Map.of("type", "string"));
		properties.put("intermediateHubs", Map.of("type", "array", "items", Map.of("type", "string")));
		properties.put("estimatedDeliveryTime", Map.of("type", "string"));
		properties.put("finalDeliveryStartTime", Map.of("type", "string"));
		responseSchema.put("properties", properties);

		responseSchema.put("required",
			List.of("messageContent", "messageTitle", "orderNumber", "orderStartDate", "originHub", "intermediateHubs",
				"destinationHub", "finalDeliveryStartTime"));
		responseSchema.put("propertyOrdering",
			List.of("messageContent", "messageTitle", "orderNumber", "originHub", "orderStartDate", "intermediateHubs",
				"destinationHub", "finalDeliveryStartTime"));
		generationConfig.put("response_schema", responseSchema);

		Map<String, Object> payloadMap = new HashMap<>();
		payloadMap.put("contents", contents);
		payloadMap.put("generationConfig", generationConfig);

		String payload;
		try {
			payload = objectMapper.writeValueAsString(payloadMap);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}

		// Call Gemini API
		String response = geminiRepository.callGeminiApi(payload);

		// error check
		String errorMessage = geminiRepository.parseError(response);
		if (errorMessage != null)
			return "Gemini API 오류: " + errorMessage;

		String generatedMessage = geminiRepository.parseGeneratedMessage(response);

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
			VerificationResult result = new VerificationResult(!verifiedMsg.isEmpty(), " ", verifiedMsg);
			return result;
		} else
			return new VerificationResult(!verifiedMsg.isEmpty(), errorMessage, verifiedMsg);
	}

}
