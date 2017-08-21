package com.technogi.rdeb.client;

public class Config {
  private int pollingTime = 5;
  private String connectionUrl;

  public int getPollingTime() {
    return pollingTime;
  }

  public Config setPollingTime(int pollingTime) {
    this.pollingTime = pollingTime;
    return this;
  }

  public String getConnectionUrl() {
    return connectionUrl;
  }

  public Config setConnectionUrl(String connectionUrl) {
    this.connectionUrl = connectionUrl;
    return this;
  }
}
