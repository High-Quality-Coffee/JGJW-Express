package com.zgzg.ai.application.service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.zgzg.ai.application.dto.SendDirectMessageRequest;
import com.zgzg.ai.domain.Message;
import com.zgzg.ai.domain.persistence.MessageReposiroty;
import com.zgzg.ai.domain.persistence.SlackRepository;
import com.zgzg.ai.presentation.DTO.GenerateMessageRequest;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SlackService {

	private final SlackRepository slackRepository;
	private final MessageReposiroty messageReposiroty;

	public void sendDirectMessage(SendDirectMessageRequest requestDto){
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

	public Message saveParsedMessage(String generatedMessage, GenerateMessageRequest requestDto) {

		Message parsedMessage = parseMessage(generatedMessage);

		// 2) 추가적으로 GenerateMessageRequest의 정보를 활용해 엔티티 값 보강
		//    (예: receiverId, senderId, 등 필요한 필드를 설정)
		//parsedMessage.setReceiverId("슬랙_채널_ID_또는_사용자_ID");
		//parsedMessage.setSenderId("SYSTEM"); // 혹은 requestDto에 따라 동적으로
		//parsedMessage.setSentAt(LocalDateTime.now());

		// 3) 저장
		Message savedMessage = messageReposiroty.save(parsedMessage);
		return savedMessage;
	}

	private Message parseMessage(String generatedMessage) {

		String title = matchByRegex(generatedMessage, "제목:\\s*(.*)");
		String orderNum = matchByRegex(generatedMessage, "주문번호:\\s*(\\S+)");
		String origin = matchByRegex(generatedMessage, "출발:\\s*(.*)");
		String currentLoc = matchByRegex(generatedMessage, "현재 위치:\\s*(.*)");
		String finalDest = matchByRegex(generatedMessage, "최종 도착:\\s*(.*)");
		String estimatedTime = matchByRegex(generatedMessage, "예상(\\s*)배송(\\s*)시간:\\s*(.*)");

		Message message = new Message(generatedMessage,title,orderNum,origin,currentLoc,finalDest,estimatedTime);
		return message;
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
}
