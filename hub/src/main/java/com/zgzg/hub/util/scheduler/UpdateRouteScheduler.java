package com.zgzg.hub.util.scheduler;

import com.zgzg.hub.util.event.UpdateRouteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateRouteScheduler implements EventScheduler<UpdateRouteEvent> {

  private final ApplicationEventPublisher publisher;

  @Override
  @Scheduled(cron = "0 0 0 * * MON")
  public void publishEvent() {
    UpdateRouteEvent event = createEvent();
    publisher.publishEvent(event);
    log.info("경로 업데이트 스케쥴링: {}", event);
  }

  @Override
  public UpdateRouteEvent createEvent() {
    return new UpdateRouteEvent(this);
  }
}
