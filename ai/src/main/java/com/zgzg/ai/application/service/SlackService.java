package com.zgzg.ai.application.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgzg.ai.application.dto.SendDirectMessageRequest;
import com.zgzg.ai.domain.Message;
import com.zgzg.ai.domain.persistence.MessageReposiroty;
import com.zgzg.ai.domain.persistence.SlackRepository;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackService {

	private final SlackRepository slackRepository;
	private final MessageReposiroty messageReposiroty;
	private final ObjectMapper objectMapper;

	public void sendDirectMessage(SendDirectMessageRequest requestDto) {
		try {
			slackRepository.sendDirectMessage(requestDto.getUserId(), requestDto.getText());
		} catch (Exception e) {
			new BaseException(Code.SLACK_SEND_FAIL);
		}
	}

	public String getMessage(String id) {
		return null;
	}

	public void updateMessage(SendDirectMessageRequest requestDto) {
	}

	public Message saveParsedMessage(String generatedMessage) {

		Message parsedMessage = parseMessage(generatedMessage);

		/**
		 * TODO : 수신자 조회하는 메서드로 변경
		 */
		String reviverSlackId = "U087R317SMN";

		Message savingMessage = new Message(generatedMessage, parsedMessage.getMessageTitle(),
			parsedMessage.getOrderNumber(), parsedMessage.getOriginHub(),
			parsedMessage.getCurrentLocation(), parsedMessage.getFinalDestination(),
			parsedMessage.getEstimatedDeliveryTime(), parsedMessage.getFinalDeliveryStratTime(), reviverSlackId,
			"slackBot", LocalDateTime.now());

		Message savedMessage = messageReposiroty.save(savingMessage);
		return savedMessage;
	}

	private Message parseMessage(String generatedMessage) {
		try {
			JsonNode node = objectMapper.readTree(generatedMessage);
			String messageTitle = node.path("messageTitle").asText();
			String orderNumber = node.path("orderNumber").asText();
			String originHub = node.path("originHub").asText();


			// 경유지가 복수일 경우 분리
			JsonNode intermediateHubsNode = node.path("intermediateHubs");
			String currentLocation = "";
			if (intermediateHubsNode.isArray()) {
				List<String> hubs = new ArrayList<>();
				for (JsonNode hubNode : intermediateHubsNode) {
					hubs.add(hubNode.asText());
				}
				currentLocation = String.join(", ", hubs);
			}

			String destinationHub = node.path("destinationHub").asText();
			String workHours = node.path("workHours").asText();
			String estimatedDeliveryTime = node.path("estimatedDeliveryTime").asText();
			String finalDeliveryStratTime = node.path("finalDeliveryStartTime").asText();

			Message message = new Message(
				generatedMessage,
				messageTitle,
				orderNumber,
				originHub,
				currentLocation,
				destinationHub,
				estimatedDeliveryTime,
				finalDeliveryStratTime
			);
			return message;
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}
	}

	/**
	 * 텍스트에서 특정 패턴을 추출하기 위한 유틸리티 메서드
	 */
	private String matchByRegex(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.group(1).trim();
		}
		return null;
	}

	@Transactional
	public void deleteMessage(String id) {
		Optional<Message> message = messageReposiroty.findById(UUID.fromString(id));
		message.get().softDelete("Temp");
	}
}
