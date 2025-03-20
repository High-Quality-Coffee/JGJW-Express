package com.zgzg.user.domain.model;

import com.zgzg.common.enums.Role;
import com.zgzg.common.utils.BaseEntity;
import com.zgzg.user.presentation.request.JoinRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="p_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Setter
    private String username;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column
    @Setter
    private String slackUsername;

    @Column(nullable = false, unique = true)
    @Setter
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private Role role;

}
