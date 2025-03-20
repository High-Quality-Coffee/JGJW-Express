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
    * 허브 4000번 대
    */
   EXIST_HUB_NAME(HttpStatus.BAD_REQUEST, 4001, "이미 존재하는 허브명 입니다."),
  
   /**
    * 업체 5000 번대
    */
   COMPANY_CREATE(HttpStatus.OK, 5001, "업체 생성이 완료되었습니다."),
   COMPANY_FIND(HttpStatus.OK,5002,"업체 조회가 완료되었습니다."),
   COMPANY_UPDATE(HttpStatus.OK,5003,"업체 수정이 완료되었습니다."),
   COMPANY_DELETE(HttpStatus.OK,5004,"업체 삭제가 완료되었습니다."),
   COMANY_SEARCH(HttpStatus.OK,5005,"업체 검색이 완료되었습니다."),

   COMPANY_FIND_ERROR(HttpStatus.BAD_REQUEST, 5101, "아이디와 일치하는 업체가 없습니다."),

  	/**
	 * 주문 2000번대
	 */
	ORDER_GET_SUCCESS(HttpStatus.OK, 2000, "주문이 성공적으로 조회되었습니다."),
	ORDER_CANCEL_SUCCESS(HttpStatus.OK, 2001, "주문이 성공적으로 취소되었습니다."),

	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 2400, "해당 주문을 찾을 수 없습니다."),


    /**
     * 회원 관련 1000번대
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 100, "잘못된 입력값이 존재합니다."),
    MEMBER_NOT_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, 1001, "회원가입이 정상적으로 처리되지 않았습니다."),
    MEMBER_SAVE(HttpStatus.OK, 1002, "회원가입이 정상적으로 처리되었습니다."),
    MEMBER_NOT_EXISTS(HttpStatus.NOT_FOUND, 1003, "해당 유저를 찾을 수 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST,1004, "로그인에 실패하셨습니다."),
    TOKEN_NOT_EXISTS(HttpStatus.NOT_FOUND, 1005, "액세스 토큰이 존재하지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1006, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1007, "유효하지 않은 토큰입니다."),
    /**
     * 400번대
     */
    //유효하지 않은(잘못된) 입력값(40000 ~ 40099번대)
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 40000, "잘못된 값이 존재합니다."),
    INVALID_QUERY_PARAM(HttpStatus.BAD_REQUEST, 40001, "쿼리 파라미터 타입이 일치하지 않습니다."),
    NULL_INPUT_VALUE(HttpStatus.BAD_REQUEST, 40002, "입력값이 없는 항목이 있습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, 40003, "지원되지 않는 파일 확장자 입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40004, "잘못된 요청입니다."),
    //유효하지 않은 리소스(40100 ~ 40199번대
    CAN_NOT_FIND_RESOURCE(HttpStatus.BAD_REQUEST, 40100, "해당 리소스를 찾을 수 없습니다."),
    CAN_NOT_FIND_USER(HttpStatus.BAD_REQUEST, 40101, "해당 유저를 찾을 수 없습니다."),


  GET_HUBS_SUCCESS(HttpStatus.OK, 4105, "허브 목록 조회가 처리되었습니다"),
  GET_HUB_SUCCESS(HttpStatus.OK, 4104, "허브 조회가 처리되었습니다."),
  DELETE_HUB_SUCCESS(HttpStatus.OK, 4103, "허브 삭제가 처리되었습니다."),
  UPDATE_HUB_SUCCESS(HttpStatus.OK, 4102, "허브 수정이 처리되었습니다."),
  CREATED_HUB_SUCCESS(HttpStatus.CREATED, 4101, "허브가 생성되었습니다."),
  EXIST_HUB_NAME(HttpStatus.BAD_REQUEST, 4001, "이미 존재하는 허브명 입니다."),
  HUB_NOT_FOUND(HttpStatus.NOT_FOUND, 4002, "허브가 존재하지 않습니다."),
  PARENT_HUB_NOT_FOUND(HttpStatus.BAD_REQUEST, 4003, "중앙 허브가 존재하지 않습니다."),

  /**
   * VALIDATION 관련 100번대
   */
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST, 100, "잘못된 입력값이 존재합니다."),
  MEMBER_NOT_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, 1001, "회원가입이 정상적으로 처리되지 않았습니다."),
  MEMBER_SAVE(HttpStatus.OK, 1002, "회원가입이 정상적으로 처리되었습니다."),
  MEMBER_NOT_EXISTS(HttpStatus.NOT_FOUND, 1003, "해당 유저를 찾을 수 없습니다."),
  LOGIN_FAILED(HttpStatus.BAD_REQUEST, 1004, "로그인에 실패하셨습니다."),
  TOKEN_NOT_EXISTS(HttpStatus.NOT_FOUND, 1005, "액세스 토큰이 존재하지 않습니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 1006, "토큰이 만료되었습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1007, "유효하지 않은 토큰입니다."),
  /**
   * 400번대
   */
  //유효하지 않은(잘못된) 입력값(40000 ~ 40099번대)
  INVALID_INPUT(HttpStatus.BAD_REQUEST, 40000, "잘못된 값이 존재합니다."),
  INVALID_QUERY_PARAM(HttpStatus.BAD_REQUEST, 40001, "쿼리 파라미터 타입이 일치하지 않습니다."),
  NULL_INPUT_VALUE(HttpStatus.BAD_REQUEST, 40002, "입력값이 없는 항목이 있습니다."),
  INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, 40003, "지원되지 않는 파일 확장자 입니다."),
  BAD_REQUEST(HttpStatus.BAD_REQUEST, 40004, "잘못된 요청입니다."),
  //유효하지 않은 리소스(40100 ~ 40199번대
  CAN_NOT_FIND_RESOURCE(HttpStatus.BAD_REQUEST, 40100, "해당 리소스를 찾을 수 없습니다."),
  CAN_NOT_FIND_USER(HttpStatus.BAD_REQUEST, 40101, "해당 유저를 찾을 수 없습니다."),


    ACCESS_DENIED(HttpStatus.FORBIDDEN, 40204, "접근 권한이 없습니다."),

  /**
   * 상품 관련 6000번대
   */
  PRODUCT_CREATE(HttpStatus.OK,6000,"상품 등록을 성공하였습니다."),
  PRODUCT_NOT_CREATE(HttpStatus.BAD_REQUEST,6001,"상품 등록을 실패하였습니다."),
  PRODUCT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,6002, "동일한 상품이 이미 존재합니다. 동일한 상품이라면, 재고를 추가해주세요"),
  PRODUCT_EXISTS(HttpStatus.OK,6010,"상품 조회가 완료되었습니다."),
  PRODUCT_NOT_EXISTS(HttpStatus.BAD_REQUEST,6011,"상품 조회를 실패하였습니다."),
  PRODUCT_UPDATE(HttpStatus.OK,6020,"상품 수정이 완료되었습니다."),
  PRODUCT_NOT_UPDATE(HttpStatus.BAD_REQUEST,6021,"상품 수정을 실패했습니다."),
  PRODUCT_DELETE(HttpStatus.OK,6030, "상품 삭제가 완료되었습니다."),
  PRODUCT_NOT_DELETE(HttpStatus.BAD_REQUEST,6031, "상품 삭제를 실패했습니다."),
  PRODUCT_NO_STOCK(HttpStatus.BAD_REQUEST,6040, "상품 재고가 부족합니다."),
  PRODUCT_STOCK_NOT_ADD(HttpStatus.BAD_REQUEST,6041, "추가할 재고는 1개 이상 이어야 합니다."),
  PRODUCT_STOCK_NOT_REDUCE(HttpStatus.BAD_REQUEST, 6042, "삭제할 재고는 1개 이상이어야 합니다."),

  /**
   * 성공 0번대
   */
  SUCCESS(HttpStatus.OK, 200, "성공적으로 처리되었습니다."),
  CREATED(HttpStatus.CREATED, 201, "성공적으로 생성되었습니다."),
  ALREADY_EXISTS(HttpStatus.OK, 202, "이미 존재하는 리소스입니다."),


  //보안 관련(40200 ~ 40299번대)
  REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, 40200, "로그인이 필요합니다."),
  ACCESS_DENIED(HttpStatus.FORBIDDEN, 40204, "접근 권한이 없습니다."),


  /**
   * 500번대
   */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "예기치 못한 서버 오류가 발생했습니다."),
  INTERNAL_SERVER_MINIO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Minio 서버 오류가 발생했습니다.");

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
