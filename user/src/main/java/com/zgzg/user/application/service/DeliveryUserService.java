package com.zgzg.user.application.service;

import com.zgzg.common.exception.BaseException;
import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.user.Infrastructure.FeignClient.HubClient;
import com.zgzg.user.application.dto.DeliveryUserResponseDTO;
import com.zgzg.user.application.dto.HubResDTO;
import com.zgzg.user.domain.model.DeliveryUser;
import com.zgzg.user.domain.model.User;
import com.zgzg.user.domain.repository.DeliveryUserRepository;
import com.zgzg.user.domain.repository.UserRepository;
import com.zgzg.user.presentation.request.DeliveryUserRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryUserService {

    private final DeliveryUserRepository deliveryUserRepository;
    private final HubClient hubClient;

    //배송 담당자 한명 조회
    public DeliveryUserResponseDTO readOne(Long id){
        DeliveryUser deliveryUser = deliveryUserRepository.findByDeliveryUserId(id).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));
        if(deliveryUser.getDeletedBy()!=null){
            throw new BaseException(Code.MEMBER_CANNOT_SEARCH);
        }
        return toDTO(deliveryUser);
    }

    //배송 담당자 모두 조회
    public List<DeliveryUserResponseDTO> readAll(){
        List<DeliveryUser> deliveryUsers = deliveryUserRepository.findAll();
        List<DeliveryUserResponseDTO> deliveryUserResponseDTOS = new ArrayList<>();
        DeliveryUserResponseDTO deliveryUserResponseDTO = null;
        for(DeliveryUser deliveryUser : deliveryUsers){
            //삭제된 회원은 조회에 포함시키지 않는다.
            if(deliveryUser.getDeletedBy() != null){
                continue;
            }
            deliveryUserResponseDTO = toDTO(deliveryUser);
            deliveryUserResponseDTOS.add(deliveryUserResponseDTO);
        }
        return deliveryUserResponseDTOS;
    }


    //배송담당자 정보 수정
    @Transactional
    public void updateDeliveryUser(Long id, DeliveryUserRequestDTO deliveryUserRequestDTO){
        DeliveryUser deliveryUser = deliveryUserRepository.findByDeliveryUserId(id).orElseThrow(()->new BaseException(Code.MEMBER_NOT_EXISTS));

        //수정하려는 허브가 실제로 존재하는지 검사
        ResponseEntity<ApiResponseData<HubResDTO>> hubCheck = hubClient.getHub(deliveryUserRequestDTO.getHubId());
        System.out.println(hubCheck.getBody().getData().getHubDTO().getHubId());

        if (hubCheck.getBody().getData().getHubDTO().getHubId()==null) {
            throw new BaseException(Code.HUB_NOT_FOUND);
        }

        //수정하려는 허브가 실제로 존재한다면, 배송담당자 정보 수정 진행
        deliveryUser.setDeliveryStatus(deliveryUserRequestDTO.getDeliveryStatus());
        deliveryUser.setDeliveryType(deliveryUserRequestDTO.getDeliveryType());
        deliveryUser.setHubId(deliveryUserRequestDTO.getHubId());

        deliveryUserRepository.save(deliveryUser);

    }

    public DeliveryUserResponseDTO toDTO(DeliveryUser deliveryUser) {
        return DeliveryUserResponseDTO.builder()
                .deliveryUserId(deliveryUser.getDeliveryUserId())
                .hubId(deliveryUser.getHubId())
                .deliverySlackUsername(deliveryUser.getDeliverySlackUsername())
                .deliveryType(deliveryUser.getDeliveryType())
                .deliveryOrder(deliveryUser.getDeliveryOrder())
                .deliveryStatus(deliveryUser.getDeliveryStatus())
                .build();
    }


}
