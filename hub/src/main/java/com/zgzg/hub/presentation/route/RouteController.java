package com.zgzg.hub.presentation.route;

import static com.zgzg.common.response.Code.GET_HUB_ROUTES_SUCCESS;

import com.zgzg.common.response.ApiResponseData;
import com.zgzg.hub.application.route.dto.RoutesDTO;
import com.zgzg.hub.application.route.service.facade.RouteService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs/route")
@RequiredArgsConstructor
public class RouteController {

  private final RouteService routeService;

  @GetMapping
  public ResponseEntity<ApiResponseData<RoutesDTO>> getRoutes(
      @RequestParam("startHubId") UUID startHubId,
      @RequestParam("endHubId") UUID endHubId) {

    return ResponseEntity.ok().body(ApiResponseData.of(
        GET_HUB_ROUTES_SUCCESS.getCode(),
        GET_HUB_ROUTES_SUCCESS.getMessage(),
        routeService.getRoutes(startHubId, endHubId)
    ));
  }
}
