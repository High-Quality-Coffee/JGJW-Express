package com.zgzg.company.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zgzg.company.application.dto.CreateCompanyDTO;
import com.zgzg.company.domain.Company;
import com.zgzg.company.domain.Persistence.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;

	public Object createCompany(CreateCompanyDTO createCompanyDTO) {
		Company company = createCompanyDTO.toEntity();
		return companyRepository.save(company);
	}

	public Object getCompany(UUID id) {
		return null;
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
