package com.technogi.rdeb.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class RdebClientMockImpl implements RdebClient {

  private final Logger log = LogManager.getLogger(RdebClientMockImpl.class);


  @Override
  public void connect(Config config) {
    log.debug("Connecting with {}", config);
  }

  @Override
  public void start() {
    log.debug("Starting Rdeb Client");
  }

  @Override
  public void stop() {
    log.debug("Stopping Rdeb Client");
  }

  @Override
  public void publish(Event event) {
    log.debug("Publishing {}",event);
  }

  @Override
  public void broadcast(Event event) {
    log.debug("Broadcasting {}", event);
  }

  @Override
  public void subscribe(String id, EventHandler eventHandler) {

  }
}
