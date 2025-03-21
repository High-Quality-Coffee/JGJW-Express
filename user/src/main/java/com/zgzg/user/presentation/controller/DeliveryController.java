package com.zgzg.user.presentation.controller;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.common.response.Code;
import com.zgzg.user.application.dto.DeliveryUserResponseDTO;
import com.zgzg.user.application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deliveries/users")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    @Secured("ROLE_HUB")
    @GetMapping("/hub")
    public ResponseEntity<ApiResponseData<DeliveryUserResponseDTO>> readHubDeliveryUser(){
        return ResponseEntity.ok().body(ApiResponseData.of(Code.DELIVERY_USER_ASSIGN.getCode(),Code.DELIVERY_USER_ASSIGN.getMessage(), deliveryService.updateHubDeliveryUser()));
    }
}
