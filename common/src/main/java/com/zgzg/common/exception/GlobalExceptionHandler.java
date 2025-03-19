package com.zgzg.common.exception;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {

    //스프링에서 감지하는 에러들

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("런타임 오류 발생: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예상치 못한 오류 발생: " + e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseData<String>> handleException(BaseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseData.failure(Code.PRODUCT_NOT_EXISTS.getCode(),Code.PRODUCT_NOT_EXISTS.getMessage()));
    }

    // 커스텀 에러처리 가능 (아래 예외 핸들러 추가 하면 됨)

}
