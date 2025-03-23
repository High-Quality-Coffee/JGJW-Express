package com.zgzg.hub.util.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UpdateRouteEvent extends ApplicationEvent {

  public UpdateRouteEvent(Object source) {
    super(source);
  }
}
