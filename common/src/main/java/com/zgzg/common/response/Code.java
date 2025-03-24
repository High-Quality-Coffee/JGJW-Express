package com.zgzg.common.response;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Code {

	/**
	 * 성공 0번대
	 */
	SUCCESS(HttpStatus.OK, 200, "성공적으로 처리되었습니다."),
	CREATED(HttpStatus.CREATED, 201, "성공적으로 생성되었습니다."),
	ALREADY_EXISTS(HttpStatus.OK, 202, "이미 존재하는 리소스입니다."),

	/**
	 * 500번대
	 */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "예기치 못한 서버 오류가 발생했습니다."),
	INTERNAL_SERVER_MINIO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Minio 서버 오류가 발생했습니다."),

	/**
	 * 회원 관련 1000번대
	 */
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 100, "잘못된 입력값이 존재합니다."),
  MEMBER_NOT_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, 1001, "회원가입이 정상적으로 처리되지 않았습니다."),
  MEMBER_SAVE(HttpStatus.OK, 1002, "회원가입이 정상적으로 처리되었습니다."),
  MEMBER_NOT_EXISTS(HttpStatus.NOT_FOUND, 1003, "해당 유저를 찾을 수 없습니다."),
  LOGIN_FAILED(HttpStatus.BAD_REQUEST, 1004, "로그인에 실패하셨습니다."),
  TOKEN_NOT_EXISTS(HttpStatus.NOT_FOUND, 1005, "액세스 토큰이 존재하지 않습니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1006, "토큰이 만료되었습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1007, "유효하지 않은 토큰입니다."),
  MEMBER_EXISTS(HttpStatus.OK, 1008, "회원 조회를 성공했습니다"),
  MEMBER_UPDATE(HttpStatus.OK, 1009, "회원 정보 수정을 성공했습니다"),
  MEMBER_DELETE(HttpStatus.OK, 1010, "회원 삭제를 성공했습니다"),
  MEMBER_ALREADY_DELETE(HttpStatus.BAD_REQUEST, 1011, "이미 삭제된 회원입니다."),
  MEMBER_CANNOT_SEARCH(HttpStatus.BAD_REQUEST,1012,"삭제된 회원은 조회가 불가능합니다."),
  DELIVERY_USER_ASSIGN(HttpStatus.OK,1100, "배송담당자를 할당하였습니다."),
  DELIVERY_USER_NOT_ASSIGN(HttpStatus.OK,1100, "배송담당자를 할당할 수 없습니다."),
  

	/**
	 * 주문 2000번대
	 */
	ORDER_GET_SUCCESS(HttpStatus.OK, 2000, "주문이 성공적으로 조회되었습니다."),
	ORDER_CANCEL_SUCCESS(HttpStatus.OK, 2001, "주문이 성공적으로 취소되었습니다."),

	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 2400, "해당 주문을 찾을 수 없습니다."),
	ORDER_AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, 2401, "해당 주문에 대한 권한이 없습니다."),
	ORDER_PRODUCT_FAIL(HttpStatus.FORBIDDEN, 2402, "재고 부족으로 주문이 불가합니다."),
	ORDER_DECREASE_PRODUCT_FAIL(HttpStatus.FORBIDDEN, 2403, "재고 차감이 실패했습니다."),
	ORDER_INCREASE_PRODUCT_FAIL(HttpStatus.FORBIDDEN, 2404, "재고 원복에 실패했습니다."),

	/**
	 * 배송 3000번대
	 */
	DELIVERY_CREATE_SUCCESS(HttpStatus.CREATED, 3000, "배송이 성공적으로 생성되었습니다."),
	DELIVERY_READ_SUCCESS(HttpStatus.OK, 3001, "배송이 성공적으로 조회되었습니다."),
	DELIVERY_CANCEL_SUCCESS(HttpStatus.OK, 3002, "배송이 성공적으로 취소되었습니다."),

	DELIVERY_CANCEL_FAIL(HttpStatus.BAD_REQUEST, 3403, "배송이 시작되어 취소가 불가합니다."),
	DELIVERY_AUTH_FORBIDDEN(HttpStatus.FORBIDDEN, 3404, "해당 주문에 대한 권한이 없습니다."),
	DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, 3405, "해당 주문을 찾을 수 없습니다."),

	/**
	 * Hub 4000번
	 */

	GET_HUB_ROUTES_SUCCESS(HttpStatus.OK, 4150, "경로 조회에 성공했습니다."),
	HUB_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 4050, "경로가 존재하지 않습니다"),
	DELETE_HUB_ROUTES_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 4051, "데이터베이스 경로 실패"),

    GET_HUBS_SUCCESS(HttpStatus.OK, 4105, "허브 목록 조회가 처리되었습니다"),
    GET_HUB_SUCCESS(HttpStatus.OK, 4104, "허브 조회가 처리되었습니다."),
    DELETE_HUB_SUCCESS(HttpStatus.OK, 4103, "허브 삭제가 처리되었습니다."),
    UPDATE_HUB_SUCCESS(HttpStatus.OK, 4102, "허브 수정이 처리되었습니다."),
    CREATED_HUB_SUCCESS(HttpStatus.CREATED, 4101, "허브가 생성되었습니다."),
    EXIST_HUB_NAME(HttpStatus.BAD_REQUEST, 4001, "이미 존재하는 허브명 입니다."),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, 4002, "허브가 존재하지 않습니다."),
    PARENT_HUB_NOT_FOUND(HttpStatus.BAD_REQUEST, 4003, "중앙 허브가 존재하지 않습니다."),
    NOT_CHANGES_HUB(HttpStatus.BAD_REQUEST, 4004, "허브 변경사항이 없습니다."),
    FIRST_CHANGE_CONNECTED_HUBS(HttpStatus.BAD_REQUEST, 4005, "연결된 허브의 중앙 허브를 먼저 변경해야합니다."),
    NORMAL_HUB_MUST_HAVE_PARENT_HUB(HttpStatus.BAD_REQUEST, 4006, "연결된 중앙 허브가 필요합니다."),

  /**
	 * 업체 5000 번대
	 */
	COMPANY_CREATE(HttpStatus.OK, 5001, "업체 생성이 완료되었습니다."),
  COMPANY_FIND(HttpStatus.OK, 5002, "업체 조회가 완료되었습니다."),
  COMPANY_UPDATE(HttpStatus.OK, 5003, "업체 수정이 완료되었습니다."),
  COMPANY_DELETE(HttpStatus.OK, 5004, "업체 삭제가 완료되었습니다."),
  COMPANY_SEARCH(HttpStatus.OK, 5005, "업체 검색이 완료되었습니다."),

  COMPANY_FIND_ERROR(HttpStatus.BAD_REQUEST, 5101, "아이디와 일치하는 업체가 없습니다."),
  COMPANY_UPDATE_ERROR(HttpStatus.BAD_REQUEST, 5102, "업체 수정 권한이 없습니다."),
  COMPANY_AUTH_ERROR(HttpStatus.BAD_REQUEST, 5103, "업체 소속 허브가 아닙니다."),

  ACCESS_DENIED(HttpStatus.FORBIDDEN, 40204, "접근 권한이 없습니다."),

	/**
	 * 상품 관련 6000번대
	 */
	PRODUCT_CREATE(HttpStatus.OK, 6000, "상품 등록을 성공하였습니다."),
	PRODUCT_NOT_CREATE(HttpStatus.BAD_REQUEST, 6001, "상품 등록을 실패하였습니다."),
	PRODUCT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, 6002, "동일한 상품이 이미 존재합니다. 동일한 상품이라면, 재고를 추가해주세요"),
	PRODUCT_EXISTS(HttpStatus.OK, 6010, "상품 조회가 완료되었습니다."),
	PRODUCT_NOT_EXISTS(HttpStatus.BAD_REQUEST, 6011, "상품 조회를 실패하였습니다."),
	PRODUCT_UPDATE(HttpStatus.OK, 6020, "상품 수정이 완료되었습니다."),
	PRODUCT_NOT_UPDATE(HttpStatus.BAD_REQUEST, 6021, "상품 수정을 실패했습니다."),
	PRODUCT_DELETE(HttpStatus.OK, 6030, "상품 삭제가 완료되었습니다."),
	PRODUCT_NOT_DELETE(HttpStatus.BAD_REQUEST, 6031, "상품 삭제를 실패했습니다."),
	PRODUCT_NO_STOCK(HttpStatus.BAD_REQUEST, 6040, "상품 재고가 부족합니다."),
	PRODUCT_STOCK_NOT_ADD(HttpStatus.BAD_REQUEST, 6041, "추가할 재고는 1개 이상 이어야 합니다."),
	PRODUCT_STOCK_NOT_REDUCE(HttpStatus.BAD_REQUEST, 6042, "삭제할 재고는 1개 이상이어야 합니다."),

	/**
	 * AI/SLACK #7000
	 */
	SLACK_SUCCESS(HttpStatus.OK, 7001, "Slack 메세지 전송 성공입니다."),
	GEMINI_VERIFY_SUCCESS(HttpStatus.OK, 7002, "Slack 메세지 검증 성공입니다."),
	SLACK_MASSAGE_DELETE_SUCCESS(HttpStatus.OK, 7003, "Slack 메세지 삭제 성공입니다."),
	SLACK_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 7101, "Slack 메세지 전송 실패입니다.");

	private final HttpStatus status;
	private final Integer code;
	private final String message;

	public String getMessage(Throwable e) {
		return this.getMessage(this.getMessage() + " - " + e.getMessage());
		// 결과 예시 - "Validation error - Reason why it isn't valid"
	}

	public String getMessage(String message) {
		return Optional.ofNullable(message)
			.filter(Predicate.not(String::isBlank))
			.orElse(this.getMessage());
	}

	public String getDetailMessage(String message) {
		return this.getMessage() + " : " + message;
	}
}