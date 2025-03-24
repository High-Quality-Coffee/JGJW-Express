package com.zgzg.order.infrastructure.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyResponseDTO {

	private UUID id;

	private UUID hub_id;

	private String name;

	private String type;

	private String address;

}