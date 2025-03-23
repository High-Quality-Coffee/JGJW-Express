package com.zgzg.user.presentation.controller;

import com.zgzg.common.security.CustomUserDetails;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserSearchController {

    @Secured("ROLE_MASTER")
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestHeader("X-USER-ID") String id){

        return "hello world : " + customUserDetails.getId() + "header id : " + id;
    }
}
