package com.zgzg.ai.domain.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;

@Repository
public class SlackRepository {

	@Value("${SLACK_URL}")
	private String slackUrl;
	@Value("${SLACK_TOKEN}")
	private String slackToken;

	public void sendDirectMessage(String userId, String message) throws Exception{
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(slackToken);

		Map<String,Object> requestBody = new HashMap<>();
		requestBody.put("channel",userId);
		requestBody.put("text",message);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
		String response = restTemplate.postForObject(slackUrl, request, String.class);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(response);
		if(!jsonNode.path("ok").asBoolean()){
			throw new BaseException(Code.SLACK_SEND_FAIL);
		}

	}
}
