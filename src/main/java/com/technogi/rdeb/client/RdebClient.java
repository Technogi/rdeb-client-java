package com.technogi.rdeb.client;

public interface RdebClient {

  default RdebClient create(){
    return new RdebClientImpl();
  }
  void connect(Config config);

  void start();

  void stop();

  void publish(Event event);

  void broadcast(Event event);

  void subscribe(String id, EventHandler eventHandler);
}
