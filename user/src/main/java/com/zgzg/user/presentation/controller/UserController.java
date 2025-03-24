package com.zgzg.user.presentation.controller;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.user.application.service.UserService;
import com.zgzg.user.presentation.request.UpdateUserRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 정보 수정
    @Secured("ROLE_MASTER")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseData<String>> updateUser(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserRequestDTO updateUserRequestDTO){
        userService.updateUser(id,updateUserRequestDTO);
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_UPDATE.getCode(),Code.MEMBER_UPDATE.getMessage(),null ));
    }

    //회원 삭제
    @Secured("ROLE_MASTER")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseData<String>> deleteUser(@PathVariable("id") Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        userService.deleteUser(id, customUserDetails);
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_DELETE.getCode(),Code.MEMBER_DELETE.getMessage(),null));
    }

}
