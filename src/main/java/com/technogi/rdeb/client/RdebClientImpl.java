package com.technogi.rdeb.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RdebClientImpl implements RdebClient {

  private final Logger log = LogManager.getLogger(RdebClientImpl.class);

  private ErrorEventHandler errorEventHandler = null;
  ScheduledThreadPoolExecutor executor;
  Config config;
  Map<String, EventHandler[]> eventHandlerMap = Collections.synchronizedMap(new HashMap<>());
  private boolean active = false;
  private AtomicLong executionCounter = new AtomicLong();
  private HttpClient httpClient = new HttpClient();

  @Override
  public void connect(Config config) {
    log.debug("Connecting to {}", config.getConnectionUrl());
    this.config = config;
    executor = new ScheduledThreadPoolExecutor(config.getPoolSize());
  }

  @Override
  public void start() {
    log.info("Starting event bus client");
    active = true;
    execute();
  }

  @Override
  public void stop() {
    log.info("Stopping event bus client");
    active = false;
  }

  private void execute() {
    try {
      executionCounter.incrementAndGet();
      Event event = httpClient.loadNextEvent(config.getConnectionUrl(), config.getClientId());
      if (event != null && eventHandlerMap != null) {
        EventHandler[] handlers = eventHandlerMap.get(event.getType());
        if (handlers != null && handlers.length > 0) {
          for (EventHandler handler : handlers) {
            handler.apply(event);
          }
        }
      }
      if (event == null)
        executor.schedule(this::execute, config.getPollingTime(), TimeUnit.SECONDS);
      else execute();
    } catch (Throwable t) {
      if (errorEventHandler != null) errorEventHandler.apply(t);
      else
        log.error("Error while porcessing a message", t);
      executor.schedule(this::execute, config.getPollingTime(), TimeUnit.SECONDS);
    }
  }


  public boolean isActive() {
    return active;
  }

  public Config getConfig() {
    return config;
  }

  public Long getNumberOfExecutions() {
    return executionCounter.get();
  }

  @Override
  public void publish(Event event) {
    log.trace("Emitting event {}", event);
    httpClient.postEvent(config.getConnectionUrl(), event);
  }

  @Override
  public void broadcast(Event event) {

  }

  @Override
  public void subscribe(String id, EventHandler eventHandler) {
    if (eventHandlerMap.containsKey(id)) {
      eventHandlerMap.put(id, appendArray(eventHandler, eventHandlerMap.get(id)));
    } else {
      eventHandlerMap.put(id, new EventHandler[]{eventHandler});
    }
  }

  public void onError(ErrorEventHandler handler) {
    this.errorEventHandler = handler;
  }

  private EventHandler[] appendArray(EventHandler event, EventHandler[] events) {
    EventHandler[] newEvents = new EventHandler[events.length + 1];
    System.arraycopy(events, 0, newEvents, 0, events.length);
    newEvents[events.length] = event;
    return newEvents;
  }
}
