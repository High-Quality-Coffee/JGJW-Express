package com.zgzg.order.infrastructure.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.order.infrastructure.dto.CompanyResponseDTO;

@FeignClient(name = "company")
public interface FeignCompanyClient {

	@GetMapping("/api/v1/companies/{id}")
	ApiResponseData<CompanyResponseDTO> getCompany(@PathVariable(name = "id") UUID id);
}
