package com.zgzg.user.presentation.controller;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.user.application.dto.UserResponseDTO;
import com.zgzg.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserSearchController {

    private final UserService userService;


    @Secured("ROLE_MASTER")
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestHeader("X-USER-ID") String id){

        return "hello world : " + customUserDetails.getId() + "header id : " + id;
    }

    @Secured("ROLE_MASTER")
    @GetMapping("")
    public ResponseEntity<ApiResponseData<List<UserResponseDTO>>> readAll(){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_EXISTS.getCode(),Code.MEMBER_EXISTS.getMessage(),userService.readAll()));
    }

    //마스터,허브 권한을 갖고 특정 유저의 정보 조회
    @Secured({"ROLE_MASTER","ROLE_HUB"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseData<UserResponseDTO>> readOne(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_EXISTS.getCode(), Code.MEMBER_EXISTS.getMessage(),userService.readOne(id)));
    }

    //내 정보 조회
    @Secured({"ROLE_MASTER","ROLE_HUB","ROLE_DELIVERY", "ROLE_STORE"})
    @GetMapping("/mine")
    public ResponseEntity<ApiResponseData<UserResponseDTO>> readMyOne(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_EXISTS.getCode(), Code.MEMBER_EXISTS.getMessage(),userService.readMyOne(customUserDetails)));
    }

}
