package com.zgzg.hub.application.route.client;

import com.zgzg.hub.application.route.dto.OrganizedRouteDTO;
import com.zgzg.hub.application.route.dto.ProcessedRouteDTO;
import com.zgzg.hub.application.route.service.NaverRouteService;
import com.zgzg.hub.application.route.service.RouteProcessingService;
import com.zgzg.hub.application.route.service.RouteStorageService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteClient {

  private final NaverRouteService naverRouteService;
  private final RouteProcessingService routeProcessingService;
  private final RouteStorageService routeStorageService;

  public void updateAllRoutes() {
    OrganizedRouteDTO organizedRouteDTO = naverRouteService.getRoutes();
    log.info("경로 추출 완료 {}", organizedRouteDTO);

    Map<String, ProcessedRouteDTO> processedRoute = routeProcessingService.processRoute(
        organizedRouteDTO);
    log.info("경로 계산 완료 {}", processedRoute);
    
    routeStorageService.saveCache(processedRoute);
    log.info("경로 redis 저장");

    routeStorageService.save(processedRoute);
    log.info("경로 데이터 로그 확인");
  }
}
