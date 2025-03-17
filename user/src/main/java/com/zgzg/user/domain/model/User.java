package com.zgzg.user.domain.model;

import com.zgzg.common.enums.Role;
import com.zgzg.common.utils.BaseEntity;
import com.zgzg.user.presentation.request.JoinRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="p_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User(JoinRequestDTO joinRequestDTO){
        this.username = joinRequestDTO.getUsername();
        this.password = joinRequestDTO.getPassword();
        this.nickname = joinRequestDTO.getNickname();
    }

}
