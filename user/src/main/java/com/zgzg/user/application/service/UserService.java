package com.zgzg.user.application.service;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.common.security.CustomUserDetails;
import com.zgzg.user.application.dto.UserResponseDTO;
import com.zgzg.user.domain.model.User;
import com.zgzg.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> readAll(){
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        UserResponseDTO userResponseDTO = null;
        for(User user : users){
            userResponseDTO = toUserResponseDTO(user);
            userResponseDTOS.add(userResponseDTO);
            userResponseDTO=null;
        }
        return userResponseDTOS;
    }

    public UserResponseDTO readOne(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));
        UserResponseDTO userResponseDTO = toUserResponseDTO(user);
        return userResponseDTO;
    }

    public UserResponseDTO readMyOne(CustomUserDetails customUserDetails){
        User user = userRepository.findById(customUserDetails.getId()).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));
        UserResponseDTO userResponseDTO = toUserResponseDTO(user);
        return userResponseDTO;
    }

    public UserResponseDTO toUserResponseDTO(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .slackUsername(user.getSlackUsername())
                .role(user.getRole())
                .build();
    }

}
