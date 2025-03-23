package com.zgzg.company;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
	@WithMockUser(username = "masterUser", roles = {"ROLE_MASTER"})
	void createCompany() {

		//CreateCompanyRequestDTO requestDTO = new CreateCompanyRequestDTO("테스트업체", "sell", "서울특별시 강남구",
		//	"3fa85f64-5717-4562-b3fc-2c963f66afa6", "1");
		CustomUserDetails userDetails = new CustomUserDetails("masterUser", "ROLE_MASTER", 1L);


		//companyService.createCompany(requestDTO, userDetails);


		companyRepository.flush();


		TestTransaction.flagForCommit();
		TestTransaction.end();
	}


}
