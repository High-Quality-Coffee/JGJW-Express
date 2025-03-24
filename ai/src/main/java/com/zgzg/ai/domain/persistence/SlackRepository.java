package com.zgzg.ai.domain.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class SlackRepository {

	@Value("${SLACK_URL}")
	private String slackUrl;
	@Value("${SLACK_TOKEN}")
	private String slackToken;
	@Value("${SLACK_CONVERSATIONS_OPEN_URL}")
	private String slackConversationsOpenUrl;

	public void sendDirectMessage(String userId, String message) throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		// HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(slackToken);

		// 요청 페이로드 구성
		Map<String, Object> requestBody = new HashMap<>();
		// 대상 사용자의 ID를 채널로 지정하면 Slack이 DM 채널을 자동 생성하여 메시지를 전송합니다.
		requestBody.put("channel", userId);
		requestBody.put("text", message);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
		String response = restTemplate.postForObject(slackUrl, request, String.class);

		// 응답에서 성공 여부 확인
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(response);
		if (!jsonNode.path("ok").asBoolean()) {
			throw new RuntimeException("메시지 전송에 실패했습니다. 응답: " + response);
		}

	}
}
