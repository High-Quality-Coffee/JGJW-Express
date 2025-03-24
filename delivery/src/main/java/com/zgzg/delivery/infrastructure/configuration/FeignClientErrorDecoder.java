package com.zgzg.delivery.infrastructure.configuration;

import static org.apache.logging.log4j.util.Strings.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class FeignClientErrorDecoder {

	private final ObjectMapper objectMapper;

	@Bean
	public ErrorDecoder decoder() {

		return (methodKey, response) -> {
			GlobalErrorResponse errorResponse = extractError(response);
			loggingError(methodKey, response, errorResponse);

			// throws CustomException
			try {
				throw new BusinessException(
					HttpStatus.valueOf(response.status()),
					errorResponse.getCode(),
					errorResponse.getMessage()
				);
			} catch (BusinessException e) {
				throw new RuntimeException(e);
			}
		};
	}

	// parse to CustomErrorResponse
	private GlobalErrorResponse extractError(Response response) {
		try (InputStream responseBodyStream = response.body().asInputStream()) {
			String body = StreamUtils.copyToString(responseBodyStream, StandardCharsets.UTF_8);
			return objectMapper.readValue(body, GlobalErrorResponse.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void loggingError(String methodKey, Response response, GlobalErrorResponse errorResponse) {
		Request request = response.request();

		log.error(""" 
				\s
				    Request Fail: {}
				    URL: {} {}
				    Status: {}
				    Request Header: {}
				    Request Body: {}
				    Response Body: {}
				\s""",
			methodKey,
			request.httpMethod(),
			request.url(),
			response.status(),
			extractRequestHeader(request),
			extractRequestBody(request),
			errorResponse
		);
	}

	private static String extractRequestHeader(Request request) {
		Map<String, Collection<String>> headers = request.headers();

		if (Objects.nonNull(headers)) {
			return headers.toString();
		}
		return EMPTY;
	}

	private static String extractRequestBody(Request request) {
		byte[] body = request.body();

		if (Objects.nonNull(body)) {
			return new String(body, StandardCharsets.UTF_8);
		}
		return EMPTY;
	}
}