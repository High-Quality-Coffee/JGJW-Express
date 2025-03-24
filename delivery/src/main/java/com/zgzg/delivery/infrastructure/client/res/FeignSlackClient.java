package com.zgzg.delivery.infrastructure.client.res;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.infrastructure.client.req.GenerateMessageRequest;

@FeignClient(name = "ai")
public interface FeignSlackClient {

	@GetMapping("/api/v1/slack/messages")
	ApiResponseData<?> createSlackMessage(@RequestBody GenerateMessageRequest requestDTO);
}
