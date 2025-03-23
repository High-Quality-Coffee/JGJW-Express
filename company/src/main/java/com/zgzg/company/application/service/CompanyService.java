package com.zgzg.company.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.company.domain.Company;
import com.zgzg.company.domain.Persistence.CompanyRepository;
import com.zgzg.company.infrastructure.client.FeginServiceClient;
import com.zgzg.company.infrastructure.client.dto.HubResDTO;
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
	private final FeginServiceClient feginServiceClient;

	public void createCompany(CreateCompanyRequestDTO createCompanyRequestDTO, CustomUserDetails userDetails) {
		Company company = createCompanyRequestDTO.toEntity();

		// 담당 허브만 생성 가능
		//getHub_id()에서 존재하는 허브인지 체크 됨
		if (!(getHubAdmin(fetchHubInfo(company.getHub_id())) == userDetails.getId()) && (
			userDetails.getRole() == "ROLE_HUB")) {
			throw new BaseException(Code.COMPANY_AUTH_ERROR);
		}

		companyRepository.save(company);
	}

	public CompanyResponseDTO getCompany(UUID id) {
		Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new BaseException(Code.COMPANY_FIND_ERROR));
		return company.toDTO();
	}

	public PageableResponseDTO<CompanyResponseDTO> getCompanies(PageableRequestDTO pageableRequestDTO) {

		Sort sort = Sort.unsorted();
		if (pageableRequestDTO.getSortBy() != null && !pageableRequestDTO.getSortBy().isEmpty()) {
			sort = Sort.by(pageableRequestDTO.getDirection(), pageableRequestDTO.getSortBy());
		}

		Pageable pageable = PageRequest.of(pageableRequestDTO.getPage(), pageableRequestDTO.getSize(), sort);
		Page<Company> companiesPage = (Page<Company>)companyRepository.findAllByDeletedAtIsNull(pageable);

		return PageableResponseDTO.from(companiesPage, CompanyResponseDTO::toDto);
	}

	public void updateCompany(UpdateCompanyRequestDTO updateCompanyRequestDTO, CustomUserDetails userDetails) {
		Company company = companyRepository.findByIdAndDeletedAtIsNull(updateCompanyRequestDTO.getId())
			.orElseThrow(() -> new BaseException(Code.COMPANY_FIND_ERROR));

		if ((!(company.getCompanyAdminId().equals(userDetails.getId()))) && (userDetails.getRole() == "ROLE_STORE")) {
			throw new BaseException(Code.COMPANY_AUTH_ERROR);
		}
		if (!(getHubAdmin(fetchHubInfo(company.getHub_id())) == userDetails.getId()) && (
			userDetails.getRole() == "ROLE_HUB")) {
			throw new BaseException(Code.COMPANY_AUTH_ERROR);
		}


		company.update(updateCompanyRequestDTO.getName(),
			updateCompanyRequestDTO.getType(),
			updateCompanyRequestDTO.getAddress());

	}

	@Transactional(readOnly = false)
	public void deleteCompany(UUID id, CustomUserDetails userDetails) {
		Company company = companyRepository.findByIdAndDeletedAtIsNull(id)
			.orElseThrow(() -> new BaseException(Code.COMPANY_FIND_ERROR));

		if (!(fetchHubInfo(company.getHub_id()).getHubAdminId() == userDetails.getId()) && (
			userDetails.getRole() == "ROLE_HUB")) {
			throw new BaseException(Code.COMPANY_AUTH_ERROR);
		}

		company.softDelete(userDetails.getUsername());
		companyRepository.save(company);
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

	public HubResDTO fetchHubInfo(UUID hubId) {
		return feginServiceClient.getHub(hubId);
	}

	public Long getHubAdmin(HubResDTO hubResDTO) {
		return hubResDTO.getHubAdminId();
	}
}
