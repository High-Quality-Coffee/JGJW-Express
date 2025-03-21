package com.zgzg.hub.util.event.handler;

import com.zgzg.hub.application.route.client.RouteClearClient;
import com.zgzg.hub.application.route.client.RouteClient;
import com.zgzg.hub.util.event.UpdateRouteEvent;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateRouteEventHandler {

  private final RouteClient routeClient;
  private final RouteClearClient routeClearClient;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
  public void handleUpdateRouteEvent(UpdateRouteEvent event) {
    CompletableFuture<Void> redisFuture = routeClearClient.redisRouteClear();
    CompletableFuture<Void> dbFuture = routeClearClient.databaseRouteClear();
    CompletableFuture.allOf(redisFuture, dbFuture).join();

    routeClient.updateAllRoutes();
    log.info("경로 update event 처리 : {}", event.getSource());
  }
}
