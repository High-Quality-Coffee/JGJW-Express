package com.zgzg.hub.presentation;

import com.zgzg.hub.application.route.client.RouteClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs/route")
@RequiredArgsConstructor
public class RouteController {

  private final RouteClient routeClient;

  @GetMapping
  public ResponseEntity<String> update() {
    routeClient.updateAllRoutes();
    return ResponseEntity.ok().body("성공");
  }
}
