package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.application.client.CompanyClient;
import com.zgzg.order.infrastructure.dto.CompanyResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CompanyClientImpl implements CompanyClient {

	private final FeignCompanyClient feignCompanyClient;

	@Override
	public CompanyResponseDTO getCompany(UUID supplierCompanyId) {
		// todo. company 연동 예외 처리
		ApiResponseData<CompanyResponseDTO> response = feignCompanyClient.getCompany(supplierCompanyId);
		log.info("Feign response result : {} , {}", response.getCode(), response.getMessage());
		return response.getData();
	}
}
