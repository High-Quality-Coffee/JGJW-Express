package com.zgzg.company.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.company.domain.Company;
import com.zgzg.company.domain.Persistence.CompanyRepository;
import com.zgzg.company.presentation.dto.CompanyResponseDTO;
import com.zgzg.company.presentation.dto.CreateCompanyRequestDTO;
import com.zgzg.company.presentation.dto.PageableRequestDTO;
import com.zgzg.company.presentation.dto.PageableResponseDTO;
import com.zgzg.company.presentation.dto.UpdateCompanyRequestDTO;

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
		Company company = companyRepository.findById(id).orElseThrow(() -> new BaseException(Code.COMPANY_FIND_ERROR));
		return company.toDTO();
	}

	public PageableResponseDTO<CompanyResponseDTO> getCompanies(PageableRequestDTO pageableRequestDTO) {

		Sort sort = Sort.unsorted();
		if (pageableRequestDTO.getSortBy() != null && !pageableRequestDTO.getSortBy().isEmpty()) {
			sort = Sort.by(pageableRequestDTO.getDirection(), pageableRequestDTO.getSortBy());
		}

		Pageable pageable = PageRequest.of(pageableRequestDTO.getPage(), pageableRequestDTO.getSize(), sort);
		Page<Company> companiesPage = companyRepository.findAll(pageable);

		return PageableResponseDTO.from(companiesPage, CompanyResponseDTO::toDto);
	}

	public void updateCompany(UpdateCompanyRequestDTO updateCompanyRequestDTO) {
		Company company = companyRepository.findById(updateCompanyRequestDTO.getId())
			.orElseThrow(() -> new BaseException(Code.COMPANY_FIND_ERROR));

		company.update(updateCompanyRequestDTO.getName(),
			updateCompanyRequestDTO.getType(),
			updateCompanyRequestDTO.getAddress());

	}

	public void deleteCompany(UUID id) {
		Company company = companyRepository.findById(id)
			.orElseThrow(() -> new BaseException(Code.COMPANY_FIND_ERROR));

		company.softDelete("temp");
	}

	public Object searchCompany(PageableRequestDTO pageableRequestDTO, String keyword) {
		Sort sort = Sort.unsorted();
		if (pageableRequestDTO.getSortBy() != null && !pageableRequestDTO.getSortBy().isEmpty()) {
			sort = Sort.by(pageableRequestDTO.getDirection(), pageableRequestDTO.getSortBy());
		}
		Pageable pageable = PageRequest.of(pageableRequestDTO.getPage(), pageableRequestDTO.getSize(), sort);
		Page<Company> companiesPage = companyRepository.searchCompanies(keyword, pageable);
		return PageableResponseDTO.from(companiesPage, CompanyResponseDTO::toDto);
	}
}
