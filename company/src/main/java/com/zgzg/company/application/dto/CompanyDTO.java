package com.zgzg.company.application.dto;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CompanyDTO {
	private UUID id;

	private UUID hub_id;

	private String name;

	private String type;

	private String address;
}
