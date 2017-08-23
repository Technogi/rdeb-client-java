package com.technogi.rdeb.client;

public interface EventBusClient {
  void connect(Config config);

  void start();

  void stop();

  void emit(Event event);

  void broadcast(Event event);

  void subscribe(String id, EventHandler eventHandler);
}
