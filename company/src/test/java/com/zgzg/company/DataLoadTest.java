package com.zgzg.company;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.transaction.TestTransaction;

import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.company.application.service.CompanyService;
import com.zgzg.company.domain.Persistence.CompanyRepository;
import com.zgzg.company.infrastructure.client.FeginServiceClient;
import com.zgzg.company.infrastructure.client.dto.HubDTO;
import com.zgzg.company.infrastructure.client.dto.HubResDTO;
import com.zgzg.company.presentation.dto.CreateCompanyRequestDTO;

@SpringBootTest
public class DataLoadTest {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyRepository companyRepository;

	@MockBean
	private FeginServiceClient feginServiceClient; // 허브 조회용 FeignClient

	@Test
	@WithMockUser(username = "masterUser", roles = {"MASTER"})
	void createCompany() {
		CustomUserDetails userDetails = new CustomUserDetails("masterUser", "ROLE_MASTER", 1L);

		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UUID fakeHubId = UUID.fromString("3d5575b4-08a9-4af8-9446-bc3aa8df4a4a");

		HubDTO mockHubDTO = HubDTO.builder()
			.hubId(fakeHubId)
			.HubName("대구HUB")
			.hubAddress("대구 북구 태평로 161")
			.hubLatitude("35.87604275518246")
			.hubLongitude("128.5913013539383")
			.hubAdminId(1L)
			.isMegaHub(true)
			.build();

		HubResDTO mockHubResDTO = new HubResDTO(mockHubDTO);

		Mockito.when(feginServiceClient.getHub(fakeHubId))
			.thenReturn(mockHubResDTO);

		CreateCompanyRequestDTO requestDTO = CreateCompanyRequestDTO.builder()
			.name("테스트업체")
			.type("sell")
			.address("서울특별시 강남구")
			.hub_id(fakeHubId)
			.companyAdminId(1L)
			.build();


		companyService.createCompany(requestDTO, userDetails);


		companyRepository.flush();

	}


}
