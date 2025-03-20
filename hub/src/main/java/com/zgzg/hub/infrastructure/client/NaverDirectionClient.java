package com.zgzg.hub.infrastructure.client;


import com.zgzg.hub.config.NaverDirectionConfig;
import com.zgzg.hub.infrastructure.client.dto.NaverRouteResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverMapApi",
    url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving",
    configuration = NaverDirectionConfig.class)
public interface NaverDirectionClient {

  @GetMapping
  NaverRouteResDTO getNaverRoute(
      @RequestParam("goal") String goal,
      @RequestParam("start") String start);
}