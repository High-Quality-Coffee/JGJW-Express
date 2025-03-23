package com.zgzg.user.application.service;

import com.zgzg.common.enums.Role;
import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.Code;
import com.zgzg.user.domain.model.DeliveryStatus;
import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.model.User;
import com.zgzg.user.domain.repository.DeliveryUserRepository;
import com.zgzg.user.domain.repository.UserRepository;
import com.zgzg.user.presentation.request.JoinDeliveryUserRequestDTO;
import com.zgzg.user.presentation.request.JoinRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final DeliveryUserRepository deliveryUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void hub_join(JoinRequestDTO joinRequestDTO){
        User user = toUser(joinRequestDTO);
        user.setRole(Role.ROLE_HUB);
        userRepository.save(user).orElseThrow(() -> new BaseException(Code.MEMBER_NOT_SAVE));
    }

    public void delivery_join(JoinDeliveryUserRequestDTO joinDeliveryUserRequestDTO){
        //배송 담당자 회원가입 (유저 테이블에 저장)
        User user = User.builder()
                .username(joinDeliveryUserRequestDTO.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDeliveryUserRequestDTO.getPassword()))
                .slackUsername(joinDeliveryUserRequestDTO.getSlackUsername())
                .nickname(joinDeliveryUserRequestDTO.getNickname())
                .role(Role.ROLE_DELIVERY)
                .build();

        User saveUser = userRepository.save(user).orElseThrow(()->new BaseException(Code.MEMBER_NOT_SAVE));

        //배송 담당자 할당 (배송담당자 테이블에 저장)
        DeliveryUser deliveryUser = DeliveryUser.builder()
                .deliveryUserId(saveUser.getId())
                .hubId(joinDeliveryUserRequestDTO.getHubId())
                .deliverySlackUsername(joinDeliveryUserRequestDTO.getSlackUsername())
                .deliveryType(joinDeliveryUserRequestDTO.getDeliveryType())
                .deliveryOrder(saveUser.getId())
                .deliveryStatus(DeliveryStatus.CAN_DELIVER)
                .build();
        deliveryUserRepository.save(deliveryUser).orElseThrow(()->new BaseException(Code.MEMBER_NOT_SAVE));

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
                .slackUsername(joinRequestDTO.getSlackUsername())
                .build();
    }

}
