package com.technogi.rdeb.client;

@FunctionalInterface
public interface ErrorEventHandler {

  void apply(Throwable error);
}
