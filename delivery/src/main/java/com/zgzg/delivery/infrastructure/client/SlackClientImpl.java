package com.zgzg.delivery.infrastructure.client;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.application.client.SlackClient;
import com.zgzg.delivery.infrastructure.client.req.GenerateMessageRequest;
import com.zgzg.delivery.infrastructure.client.res.FeignSlackClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackClientImpl implements SlackClient {

	private final FeignSlackClient feignSlackClient;

	@Override
	public void createSlackMessage(GenerateMessageRequest messageRequest) {
		ApiResponseData<?> response = feignSlackClient.createSlackMessage(messageRequest);
		log.info(response.getMessage());
	}
}
