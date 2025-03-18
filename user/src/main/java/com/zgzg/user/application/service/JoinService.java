package com.zgzg.user.application.service;

import com.zgzg.common.enums.Role;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.user.domain.model.User;
import com.zgzg.user.domain.repository.UserRepository;
import com.zgzg.user.presentation.request.JoinRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void hub_join(JoinRequestDTO joinRequestDTO){
        User user = toUser(joinRequestDTO);
        user.setRole(Role.ROLE_HUB);
        userRepository.save(user).orElseThrow(() -> new BaseException(Code.MEMBER_NOT_SAVE));
    }

    public void delivery_join(JoinRequestDTO joinRequestDTO){
        User user = toUser(joinRequestDTO);
        user.setRole(Role.ROLE_DELIVERY);
        userRepository.save(user).orElseThrow(()->new BaseException(Code.MEMBER_NOT_SAVE));
    }

    public void store_join(JoinRequestDTO joinRequestDTO){
        User user = toUser(joinRequestDTO);
        user.setRole(Role.ROLE_STORE);
        userRepository.save(user).orElseThrow(()->new BaseException(Code.MEMBER_NOT_SAVE));
    }

    public void master_join(JoinRequestDTO joinRequestDTO){
        User user = toUser(joinRequestDTO);
        user.setRole(Role.ROLE_MASTER);
        userRepository.save(user).orElseThrow(()->new BaseException(Code.MEMBER_NOT_SAVE));
    }

    public User toUser(JoinRequestDTO joinRequestDTO){
        return User.builder()
                .username(joinRequestDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequestDTO.getPassword()))
                .nickname(joinRequestDTO.getNickname())
                .build();
    }

}
