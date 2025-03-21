package com.zgzg.user.presentation.request;

import com.zgzg.common.enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequestDTO {

    @Size(min = 4, max = 10, message = "유저네임은 4자 이상, 10자 이하여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "유저네임은 알파벳 소문자(a-z)와 숫자(0-9)만 포함해야 합니다.")
    private String username;


    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+=\\-\\[\\]{};':\",.<>/?]+$",
            message = "비밀번호는 알파벳 대소문자(a-z, A-Z), 숫자(0-9), 특수문자만 포함해야 합니다.")
    private String password;

    @Size(min = 4, max = 10, message = "닉네임은 4자 이상, 10자 이하여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "닉네임은 알파벳 소문자(a-z)와 숫자(0-9)만 포함해야 합니다.")
    private String nickname;

    private String slackUsername;

}
