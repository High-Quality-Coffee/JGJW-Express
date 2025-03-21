package com.zgzg.hub.util.scheduler;

import org.springframework.context.ApplicationEvent;

public interface EventScheduler<T extends ApplicationEvent> {

  void publishEvent();

  T createEvent();
}
