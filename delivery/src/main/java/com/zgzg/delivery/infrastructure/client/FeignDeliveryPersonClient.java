package com.zgzg.delivery.infrastructure.client;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.delivery.infrastructure.client.res.DeliveryUserResponseDTO;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user")
public interface FeignDeliveryPersonClient {

  @GetMapping("/api/v1/delivery/users/hub")
  ApiResponseData<DeliveryUserResponseDTO> getHubDeliveryPerson();

  @GetMapping("/api/v1/delivery/users/store/{hubId}")
  ApiResponseData<DeliveryUserResponseDTO> getStoreDeliveryPerson(
      @PathVariable("hubId") UUID hubId);

  @PutMapping("/api/v1/delivery/users/{id}")
  ApiResponseData<DeliveryUserResponseDTO> updateDeliveryPerson(@PathVariable("id") UUID id);

  // todo. requestBody 추가 필요 -> API 작업 마무리 요청 (규원님)
}
