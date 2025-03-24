package com.zgzg.user.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.user.application.dto.DeliveryUserResponseDTO;
import com.zgzg.user.application.service.DeliveryService;
import com.zgzg.user.application.service.DeliveryUserService;
import com.zgzg.user.presentation.request.DeliveryUserRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/delivery/users")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryUserService deliveryUserService;

    //다건 조회
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_DELIVERY"})
    @GetMapping("")
    public ResponseEntity<ApiResponseData<List<DeliveryUserResponseDTO>>> readAll(){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_EXISTS.getCode(), Code.MEMBER_EXISTS.getMessage(), deliveryUserService.readAll()));
    }

    //단건 조회
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_DELIVERY"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseData<DeliveryUserResponseDTO>> readOne(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_EXISTS.getCode(), Code.MEMBER_EXISTS.getMessage(), deliveryUserService.readOne(id)));
    }

    //배송담당자 정보 수정
    @Secured({"ROLE_MASTER","ROLE_HUB", "ROLE_DELIVERY"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseData<String>> update(@PathVariable("id") Long id, @RequestBody DeliveryUserRequestDTO deliveryUserRequestDTO){
        deliveryUserService.updateDeliveryUser(id,deliveryUserRequestDTO);
        return ResponseEntity.ok().body(ApiResponseData.of(Code.MEMBER_UPDATE.getCode(),Code.MEMBER_UPDATE.getMessage(),null));
    }


    //허브 배송 담당자 할당
    @Secured({"ROLE_MASTER","ROLE_HUB"})
    @GetMapping("/hub")
    public ResponseEntity<ApiResponseData<DeliveryUserResponseDTO>> readHubDeliveryUser(){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.DELIVERY_USER_ASSIGN.getCode(),Code.DELIVERY_USER_ASSIGN.getMessage(), deliveryService.updateHubDeliveryUser()));
    }

    //업체 배송 담당자 할당
    @Secured({"ROLE_MASTER","ROLE_HUB"})
    @GetMapping("/store/{id}")
    public ResponseEntity<ApiResponseData<DeliveryUserResponseDTO>> readHubDeliveryUser(@PathVariable("id") UUID id){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.DELIVERY_USER_ASSIGN.getCode(),Code.DELIVERY_USER_ASSIGN.getMessage(), deliveryService.updateStoreDeliveryUser(id)));
    }


}
