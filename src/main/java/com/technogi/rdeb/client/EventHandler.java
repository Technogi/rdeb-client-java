package com.technogi.rdeb.client;

@FunctionalInterface
public interface EventHandler {

  void apply(Event event);
}
