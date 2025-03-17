package com.zgzg.common.response;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

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

  /**
   * 주문 2000번대
   */
  ORDER_GET_SUCCESS(HttpStatus.OK, 2000, "주문이 성공적으로 조회되었습니다."),
  ORDER_CANCEL_SUCCESS(HttpStatus.OK, 2001, "주문이 성공적으로 취소되었습니다."),

  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 2400, "해당 주문을 찾을 수 없습니다."),

  /**
   * Hub 4000번
   */
  GET_HUBS_SUCCESS(HttpStatus.OK, 4105, "허브 목록 조회가 처리되었습니다"),
  GET_HUB_SUCCESS(HttpStatus.OK, 4104, "허브 조회가 처리되었습니다."),
  DELETE_HUB_SUCCESS(HttpStatus.OK, 4103, "허브 삭제가 처리되었습니다."),
  UPDATE_HUB_SUCCESS(HttpStatus.OK, 4102, "허브 수정이 처리되었습니다."),
  CREATED_HUB_SUCCESS(HttpStatus.CREATED, 4101, "허브가 생성되었습니다."),
  EXIST_HUB_NAME(HttpStatus.BAD_REQUEST, 4001, "이미 존재하는 허브명 입니다."),
  HUB_NOT_FOUND(HttpStatus.NOT_FOUND, 4002, "허브가 존재하지 않습니다."),
  PARENT_HUB_NOT_FOUND(HttpStatus.BAD_REQUEST, 4003, "중앙 허브가 존재하지 않습니다."),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, 40204, "접근 권한이 없습니다."),
  /**
   * 업체 5000 번대
   */
  COMPANY_UPDATE(HttpStatus.OK, 5003, "업체 수정이 완료되었습니다."),
  COMPANY_DELETE(HttpStatus.OK, 5004, "업체 삭제가 완료되었습니다."),
  COMANY_SEARCH(HttpStatus.OK, 5005, "업체 검색이 완료되었습니다."),

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
  SLACK_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 7101, "Slack 메세지 전송 실패입니다."),
  ;

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
