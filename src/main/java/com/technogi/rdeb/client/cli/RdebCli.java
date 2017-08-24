package com.technogi.rdeb.client.cli;

import com.technogi.rdeb.client.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class RdebCli {

  public static void main(String[] args) {

    msg("Starting RDEB Cli cli");

    Scanner scanner = new Scanner(System.in);
    RdebClient client = null;
    String cmd = "";
    boolean verbose = LogManager.getLogger(RdebClient.class).isTraceEnabled();
    if (verbose) msg("Verbose mode is ON");
    while (!cmd.trim().toLowerCase().equals("q")) {
      System.out.print("> ");
      String[] cmdArgs = scanner.nextLine().split(" ");
      cmd = cmdArgs[0];

      switch (cmd.toLowerCase().trim()) {
        case "info":
          msg("Verbose mode is " + (verbose ? "ON" : "OFF"));
          if (client == null) {
            msg("Client not initialized");
          } else {
            msg("Connected to: " + ((RdebClientImpl) client).getConfig().getConnectionUrl());
            msg("Status:       " + (((RdebClientImpl) client).isActive() ? "ACTIVE" : "INACTIVE"));
            msg("Executions:   " + ((RdebClientImpl) client).getNumberOfExecutions());
          }
          break;
        case "verbose":
        case "v":
          if (!verbose) {
            Configurator.setLevel(RdebClientImpl.class.getCanonicalName(), Level.ALL);
            verbose = true;
            msg("Verbose mode is ON");
          } else {
            Configurator.setLevel(RdebClientImpl.class.getCanonicalName(), Level.DEBUG);
            verbose = false;
            msg("Verbose mode is OFF");
          }
          break;
        case "connect":
          if (client == null) client = connect();
          else msg("Client already connected");
          break;
        case "start":
          if (client == null) client = connect();
          client.start();
          break;
        case "stop":
          if (client == null) msg("Client is not connected");
          else client.stop();
          break;
        case "publish":
        case "p":
          if(cmdArgs.length<2){
            msg("Missing event type");
          }else
          publish(client, cmdArgs[1]);
          break;
        case "subscribe": case "s":
          if(cmdArgs.length<2){
            msg("Missing event type");
          }else
            subscribe(client, cmdArgs[1]);
          break;
        default:
          help();
      }
    }


    msg("Shutting down cli");
  }

  static void msg(Object o) {
    System.out.println(o);
  }

  static void publish(RdebClient client, String type) {
    Event e = new Event()
        .setType(type)
        .setProps(new Properties() {{
          put("label", UUID.randomUUID().toString());
          put("value", new Random(System.currentTimeMillis()).nextInt()+"");
        }});
    client.publish(e);
  }

  static void subscribe(RdebClient client, String type){
    String listenerId = UUID.randomUUID().toString();
    msg("Subscribing to channel "+type +" with id "+listenerId);
    client.subscribe(type, (event) -> {
      msg("GOT event on listener "+listenerId);
      msg(event);
    });
  }

  static RdebClient connect() {
    Config config = new Config()
        .setClientId("rdeb-cli")
        .setConnectionUrl("http://localhost:8080/")
        .setPollingTime(1)
        .setPoolSize(2);

    RdebClient client = new RdebClientImpl();
    client.connect(config);

    return client;
  }

  static void help() {
    msg("Help message");
  }
}
