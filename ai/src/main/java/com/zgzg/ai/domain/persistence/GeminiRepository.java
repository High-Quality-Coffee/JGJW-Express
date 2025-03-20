package com.zgzg.ai.domain.persistence;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public String callGeminiApi(String payload) throws Exception {

		String url = GEMINI_URL + GEMINI_API_KEY;

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create(url))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(payload))
			.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		log.info("gemini response : "+String.valueOf(response.body()));
		return response.body();
	}

	/**
	 * 응답에서 오류 노드 파싱
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String parseError(String response) throws Exception {
		JsonNode root = objectMapper.readTree(response);

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
	public String parseGeneratedMessage(String response) throws Exception {
		JsonNode root = objectMapper.readTree(response);

		JsonNode candidatesNode = root.path("candidates");
		if (candidatesNode.isArray() && candidatesNode.size() > 0) {
			JsonNode firstCandidate = candidatesNode.get(0);

			// "content" → "parts" 배열에서 첫 번째 요소
			JsonNode contentNode = firstCandidate.path("content");
			JsonNode partsNode = contentNode.path("parts");
			if (partsNode.isArray() && partsNode.size() > 0) {
				// 실제 텍스트는 parts[0].text
				return partsNode.get(0).path("text").asText();
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
