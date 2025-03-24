package com.zgzg.delivery.infrastructure.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalErrorResponse {

	private String code;  // 에러 코드 (ex: DELIVERY_403)
	private String message; // 에러 메시지 (ex: 접근 권한이 없습니다.)
	private int status; // HTTP 상태 코드 (ex: 403)
}

