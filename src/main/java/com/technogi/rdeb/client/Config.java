package com.technogi.rdeb.client;

import java.util.Properties;

public class Config {
  private int pollingTime = 5;
  private String connectionUrl;
  private int poolSize = Runtime.getRuntime().availableProcessors()*2;
  private String clientId;
  private Properties props = new Properties();

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

  public int getPoolSize() {
    return poolSize;
  }

  public Config setPoolSize(int poolSize) {
    this.poolSize = poolSize;
    return this;
  }

  public String getClientId() {
    return clientId;
  }

  public Config setClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public Properties getProps() {
    return props;
  }

  public Config setProps(Properties props) {
    this.props = props;
    return this;
  }

  @Override
  public String toString() {
    return "Config{" +
        "pollingTime=" + pollingTime +
        ", connectionUrl='" + connectionUrl + '\'' +
        ", poolSize=" + poolSize +
        ", clientId='" + clientId + '\'' +
        '}';
  }
}
