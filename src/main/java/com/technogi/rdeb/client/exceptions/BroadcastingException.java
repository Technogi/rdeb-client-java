package com.technogi.rdeb.client.exceptions;

public class BroadcastingException extends RuntimeException {
  public BroadcastingException() {
  }

  public BroadcastingException(String message) {
    super(message);
  }

  public BroadcastingException(String message, Throwable cause) {
    super(message, cause);
  }

  public BroadcastingException(Throwable cause) {
    super(cause);
  }

  public BroadcastingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
