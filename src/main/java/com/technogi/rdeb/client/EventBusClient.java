package com.technogi.rdeb.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventBusClient {

  private ScheduledThreadPoolExecutor executor;
  private Config config;
  private Map<String, EventHandler[]> eventHandlerMap = Collections.synchronizedMap(new HashMap<>());

  public void connect(Config config) {
    this.config = config;
  }

  public void start() {
    executor = new ScheduledThreadPoolExecutor(config.getPoolSize());
    execute();
  }

  private void execute() {
    try {
      HttpResponse<Event> resp = Unirest.get(config.getConnectionUrl())
          .header(Constants.HTTP_CLIENT_HEADER, config.getClientId())
          .asObject(Event.class);
      if (resp.getStatus() == 200) {
        Arrays.stream(eventHandlerMap.get(resp.getBody().getId())).forEach(handler -> {
          handler.apply(resp.getBody(), null);
        });
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
    executor.schedule(this::execute, config.getPollingTime(), TimeUnit.SECONDS);
  }

  public void emit(Event event) {
    try {
      Unirest.post(config.getConnectionUrl())
          .header(Constants.HTTP_CLIENT_HEADER, config.getClientId())
          .body(event)
          .asJson();
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }

  public void broadcast(Event event) {

  }

  public void subscribe(String id, EventHandler eventHandler) {
    if (eventHandlerMap.containsKey(id)) {
      eventHandlerMap.put(id, ArrayUtils.add(eventHandlerMap.get(id), eventHandler));
    } else {
      eventHandlerMap.put(id, new EventHandler[]{eventHandler});
    }
  }
}
