package com.zgzg.ai.domain.persistence;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zgzg.common.exception.BaseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class GeminiRepository {

	private final HttpClient httpClient = HttpClient.newHttpClient();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${GEMINI_URL}")
	private String GEMINI_URL;
	@Value("${GEMINI_API_KEY}")
	private String GEMINI_API_KEY;

	/**
	 * Gemini API 사용
	 * @param payload
	 * @return
	 * @throws Exception
	 */
	public String callGeminiApi(String payload) {

		String url = GEMINI_URL + GEMINI_API_KEY;

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(payload))
			.build();

		HttpResponse<String> response = null;
		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw new BaseException(e);
		} catch (InterruptedException e) {
			throw new BaseException(e);
		}

		log.info("gemini response : "+String.valueOf(response.body()));
		return response.body();
	}

	/**
	 * 응답에서 오류 노드 파싱
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String parseError(String response) {
		JsonNode root = null;
		try {
			root = objectMapper.readTree(response);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}

		if(root.has("error")) {
			return root.get("error").path("message").asText();
		}

		return null;
	}

	/**
	 * 생성된 API응답에서 텍스트 파싱
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String parseGeneratedMessage(String response){
		JsonNode root = null;
		try {
			root = objectMapper.readTree(response);
		} catch (JsonProcessingException e) {
			throw new BaseException(e);
		}

		JsonNode candidatesNode = root.path("candidates");
		if (candidatesNode.isArray() && candidatesNode.size() > 0) {
			JsonNode firstCandidate = candidatesNode.get(0);

			JsonNode contentNode = firstCandidate.path("content");
			JsonNode partsNode = contentNode.path("parts");
			if (partsNode.isArray() && partsNode.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (JsonNode part : partsNode) {
					JsonNode textNode = part.get("text");
					if (textNode != null && !textNode.isNull()) {
						sb.append(textNode.asText());
					}
				}
				return sb.toString().trim();
			}
		}
		return " ";
	}

	public String parseVerificationMessage(String response) throws Exception {
		JsonNode root = objectMapper.readTree(response);

		JsonNode candidateNode = root.path("candidates");
		if(candidateNode.isArray() && candidateNode.size() > 0){
			JsonNode firstCandidate = candidateNode.get(0);
			JsonNode contentNode = firstCandidate.path("contents");
			if(!contentNode.isMissingNode()){
				JsonNode partsNode = contentNode.path("parts");
				if(partsNode.isArray() && partsNode.size() > 0) {
					return partsNode.get(0).path("text").asText();
				}
			}
		}
		return "";
	}
}
