package com.zgzg.user.application.dto;

import com.zgzg.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private String slackUsername;
    private String nickname;
    private Role role;
}
