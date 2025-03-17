package com.zgzg.company.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.company.presentation.dto.CreateCompanyRequestDTO;
import com.zgzg.company.domain.Company;
import com.zgzg.company.domain.Persistence.CompanyRepository;
import com.zgzg.company.presentation.dto.CompanyResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;

	public void createCompany(CreateCompanyRequestDTO createCompanyRequestDTO) {
		Company company = createCompanyRequestDTO.toEntity();
		companyRepository.save(company);
	}

	public CompanyResponseDTO getCompany(UUID id) {
		Company company = companyRepository.findById(id).orElseThrow(()-> new BaseException(Code.COMPANY_FIND_ERROR));
		return company.toDTO();
	}

	public Object getCompanies() {
		return null;
	}

	public Object updateCompany() {
		return null;
	}

	public Object deleteCompany() {
		return null;
	}

	public Object searchCompany() {
		return null;
	}
}
