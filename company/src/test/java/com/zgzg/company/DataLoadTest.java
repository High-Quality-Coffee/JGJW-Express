package com.zgzg.company;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.transaction.TestTransaction;

import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.company.application.service.CompanyService;
import com.zgzg.company.domain.Persistence.CompanyRepository;
import com.zgzg.company.presentation.dto.CreateCompanyRequestDTO;

@SpringBootTest
public class DataLoadTest {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyRepository companyRepository;

	@Test
	@WithMockUser(username = "masterUser", roles = {"MASTER"})
	void createCompany() {

		CreateCompanyRequestDTO requestDTO = CreateCompanyRequestDTO.builder()
			.name("테스트업체")
			.type("sell")
			.address("서울특별시 강남구")
			.hub_id(UUID.fromString("a96f074b-43e2-4c07-8981-ff80b1e944c4"))
			.companyAdminId(1L)
			.build();
		CustomUserDetails userDetails = new CustomUserDetails("masterUser", "ROLE_MASTER", 1L);

		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		companyService.createCompany(requestDTO, userDetails);


		companyRepository.flush();

	}


}
