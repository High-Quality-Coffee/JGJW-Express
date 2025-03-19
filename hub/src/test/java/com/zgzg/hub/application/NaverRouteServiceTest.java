package com.zgzg.hub.application;

import com.zgzg.hub.application.route.service.NaverRouteService;
import com.zgzg.hub.config.NaverDirectionConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(NaverDirectionConfig.class)
class NaverRouteServiceTest {

  @Autowired
  NaverRouteService naverRouteService;

  @Test
  @DisplayName("네이버 Direction API 연동 성공")
  void naverDirectionApi() {
//    naverRouteService.getRoutes();
  }
}