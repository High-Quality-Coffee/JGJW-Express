package com.zgzg.company.presentation.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.company.presentation.dto.CreateCompanyRequestDTO;
import com.zgzg.company.application.service.CompanyService;
import com.zgzg.company.presentation.dto.CompanyResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;

	@PostMapping("/")
	public ResponseEntity<?> createCompany(@RequestBody CreateCompanyRequestDTO createCompanyRequestDTO) {
		companyService.createCompany(createCompanyRequestDTO);
		return ResponseEntity.ok().body(ApiResponseData.success(Code.COMPANY_CREATE));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getCompany(@PathVariable UUID id) {
		CompanyResponseDTO companyResponseDTO = companyService.getCompany(id);
		return ResponseEntity.ok(ApiResponseData.success(companyResponseDTO, "업체 조회가 완료되었습니다."));
	}

	@GetMapping("/")
	public ResponseEntity<?> getCompanies() {
		Object result = companyService.getCompanies();
		return ResponseEntity.ok(new ApiResponseData<>());
	}

	@PutMapping("/adj")
	public ResponseEntity<?> updateCompany() {
		Object result = companyService.updateCompany();
		return ResponseEntity.ok(new ApiResponseData<>());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> deleteCompany() {
		Object result = companyService.deleteCompany();
		return ResponseEntity.ok(new ApiResponseData<>());
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchCompany() {
		Object result = companyService.searchCompany();
		return ResponseEntity.ok(new ApiResponseData<>());
	}

}
