package com.zgzg.user.presentation.controller;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.user.application.service.JoinService;
import com.zgzg.user.presentation.request.JoinRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.zgzg.common.response.Code.MEMBER_SAVE;

@RestController
@RequestMapping("/api/v1/join")
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }


    @PostMapping("/hub")
    public ResponseEntity<ApiResponseData<String>> hub_join(@RequestBody @Valid JoinRequestDTO joinRequestDTO){
        joinService.hub_join(joinRequestDTO);
        return ResponseEntity.ok().body(ApiResponseData.of(MEMBER_SAVE.getCode(),"회원가입이 처리되었습니다.",null));
    }

    @PostMapping("/delivery")
    public ResponseEntity<ApiResponseData<String>> delivery_join(@RequestBody @Valid JoinRequestDTO joinRequestDTO){
        joinService.delivery_join(joinRequestDTO);
        return ResponseEntity.ok().body(ApiResponseData.of(MEMBER_SAVE.getCode(),"회원가입이 처리되었습니다.",null));
    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponseData<String>> store_join(@RequestBody @Valid JoinRequestDTO joinRequestDTO){
        joinService.store_join(joinRequestDTO);
        return ResponseEntity.ok().body(ApiResponseData.of(MEMBER_SAVE.getCode(),"회원가입이 처리되었습니다.",null));
    }

    @PostMapping("/master")
    public ResponseEntity<ApiResponseData<String>> master_join(@RequestBody @Valid JoinRequestDTO joinRequestDTO){
        joinService.master_join(joinRequestDTO);
        return ResponseEntity.ok().body(ApiResponseData.of(MEMBER_SAVE.getCode(),"회원가입이 처리되었습니다.",null));
    }

}
