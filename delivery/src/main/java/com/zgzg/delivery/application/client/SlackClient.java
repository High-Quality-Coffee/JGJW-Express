package com.zgzg.delivery.application.client;

import com.zgzg.delivery.infrastructure.client.req.GenerateMessageRequest;

public interface SlackClient {

	void createSlackMessage(GenerateMessageRequest messageRequest);
}
