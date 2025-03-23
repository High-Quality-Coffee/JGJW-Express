package com.zgzg.user.Infrastructure.FeignClient;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.user.application.dto.HubResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "hub")
public interface HubClient {
    @GetMapping("/api/v1/hubs/{hubId}")
    ResponseEntity<ApiResponseData<HubResDTO>> getHub(
            @PathVariable("hubId") UUID hubId);
}
