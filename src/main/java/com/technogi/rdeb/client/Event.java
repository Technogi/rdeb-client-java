package com.technogi.rdeb.client;

import java.io.Serializable;
import java.util.Properties;

public class Event implements Serializable{

  private String id;
  private Properties props = new Properties();

  public String getId() {
    return id;
  }

  public Event setId(String id) {
    this.id = id;
    return this;
  }

  public Properties getProps() {
    return props;
  }

  public Event setProps(Properties props) {
    this.props = props;
    return this;
  }
}
