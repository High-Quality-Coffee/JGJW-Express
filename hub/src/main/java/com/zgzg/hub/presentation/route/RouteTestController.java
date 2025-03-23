package com.zgzg.hub.presentation.route;

import com.zgzg.hub.application.route.client.RouteClearClient;
import com.zgzg.hub.application.route.client.RouteClient;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hubs/route")
@RequiredArgsConstructor
public class RouteTestController {

  private final RouteClearClient routeClearClient;
  private final RouteClient routeClient;

  @GetMapping("/update")
  public ResponseEntity<String> update() {
    CompletableFuture<Void> redisFuture = routeClearClient.redisRouteClear();
    CompletableFuture<Void> dbFuture = routeClearClient.databaseRouteClear();
    CompletableFuture.allOf(redisFuture, dbFuture).join();
    
    routeClient.updateAllRoutes();
    return ResponseEntity.ok().body("성공");
  }
}
