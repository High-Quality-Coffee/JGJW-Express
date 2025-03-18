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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.company.application.service.CompanyService;
import com.zgzg.company.presentation.dto.CompanyResponseDTO;
import com.zgzg.company.presentation.dto.CreateCompanyRequestDTO;
import com.zgzg.company.presentation.dto.PageableRequestDTO;
import com.zgzg.company.presentation.dto.PageableResponseDTO;
import com.zgzg.company.presentation.dto.UpdateCompanyRequestDTO;

import jakarta.validation.Valid;
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
	public ResponseEntity<?> getCompany(@PathVariable(name = "id") UUID id) {
		CompanyResponseDTO companyResponseDTO = companyService.getCompany(id);
		return ResponseEntity.ok(ApiResponseData.success(companyResponseDTO, Code.COMPANY_FIND.getMessage()));
	}

	@GetMapping("/")
	public ResponseEntity<?> getCompanies(@Valid PageableRequestDTO pageableRequestDTO) {
		PageableResponseDTO<CompanyResponseDTO> result = companyService.getCompanies(pageableRequestDTO);
		return ResponseEntity.ok(ApiResponseData.success(result, Code.COMPANY_FIND.getMessage()));
	}

	@PutMapping("/adj")
	public ResponseEntity<?> updateCompany(@RequestBody UpdateCompanyRequestDTO updateCompanyRequestDTO) {
		companyService.updateCompany(updateCompanyRequestDTO);
		return ResponseEntity.ok(ApiResponseData.success(Code.COMPANY_UPDATE));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") UUID id) {
		companyService.deleteCompany(id);
		return ResponseEntity.ok(ApiResponseData.success(Code.COMPANY_DELETE));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchCompany(PageableRequestDTO pageableRequestDTO,
		@RequestParam(name = "keyword", required = false) String keyword) {
		Object result = companyService.searchCompany(pageableRequestDTO, keyword);
		return ResponseEntity.ok(ApiResponseData.success(result, Code.COMANY_SEARCH.getMessage()));
	}

}
