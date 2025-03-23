package com.zgzg.hub.application.route.client;

import static com.zgzg.common.response.Code.DELETE_HUB_ROUTES_FAILED;

import com.zgzg.common.exception.BaseException;
import com.zgzg.hub.domain.repository.route.RouteRepository;
import com.zgzg.hub.infrastructure.redis.RedisHashUtil;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class RouteClearClient {

  private static final String PREFIX = "hub:";

  private final RedisHashUtil redisHashUtil;
  private final RouteRepository routeRepository;

  @Async
  public CompletableFuture<Void> redisRouteClear() {
    redisHashUtil.deleteKeysByPrefix(PREFIX);
    log.info("redis 경로 삭제, PREFIX : {}", PREFIX);
    return CompletableFuture.completedFuture(null);
  }

  @Async
  @Transactional
  public CompletableFuture<Void> databaseRouteClear() {
    try {
      routeRepository.deleteAll();
      log.info("DB 경로 삭제");
      return CompletableFuture.completedFuture(null);
    } catch (Exception e) {
      log.error("DB 경로 삭제 실패");
      throw new BaseException(DELETE_HUB_ROUTES_FAILED);
    }
  }
}
