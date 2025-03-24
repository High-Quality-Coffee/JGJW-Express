package com.zgzg.order.application.client;

import java.util.UUID;

import com.zgzg.order.infrastructure.dto.CompanyResponseDTO;

public interface CompanyClient {
	CompanyResponseDTO getCompany(UUID supplierCompanyId);
}
