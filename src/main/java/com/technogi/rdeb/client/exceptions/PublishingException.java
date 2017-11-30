package com.technogi.rdeb.client.exceptions;

public class PublishingException extends RuntimeException {
  public PublishingException() {
  }

  public PublishingException(String message) {
    super(message);
  }

  public PublishingException(String message, Throwable cause) {
    super(message, cause);
  }

  public PublishingException(Throwable cause) {
    super(cause);
  }

  public PublishingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
