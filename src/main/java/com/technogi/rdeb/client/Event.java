package com.technogi.rdeb.client;

import java.io.Serializable;
import java.util.Properties;

public class Event implements Serializable{

  private String type;
  private Properties props = new Properties();

  public String getType() {
    return type;
  }

  public Event setType(String type) {
    this.type = type;
    return this;
  }

  public Properties getProps() {
    return props;
  }

  public Event setProps(Properties props) {
    this.props = props;
    return this;
  }

  @Override
  public String toString() {
    return "Event{" +
        "type='" + type + '\'' +
        ", props=" + props +
        '}';
  }
}
