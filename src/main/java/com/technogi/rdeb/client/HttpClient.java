package com.technogi.rdeb.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

  private ObjectMapper objectMapper = new ObjectMapper();

  public void postEvent(String url, Event event){
    try {
      HttpURLConnection connection = null;
      URL urlObj = new URL(url);
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/rdeb-event");

      byte[] body = objectMapper.writeValueAsBytes(event);

      connection.setRequestProperty("Content-Length",
          Integer.toString(body.length));
      connection.setUseCaches(false);
      connection.setDoOutput(true);
      connection.getOutputStream().write(body);
      connection.getOutputStream().flush();
      connection.getOutputStream().close();


      //Get Response
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      System.out.println("RESULTADO: "+response.toString());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public Event loadNextEvent(String url, String clientId){
    try {
      HttpURLConnection connection = null;
      URL urlObj = new URL(url);
      connection = (HttpURLConnection) urlObj.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/rdeb-event");
      connection.setRequestProperty(Constants.HTTP_CLIENT_HEADER, clientId);

      //Get Response
      return objectMapper.readValue(connection.getInputStream(),Event.class);
    }catch(FileNotFoundException fnfe){
      return null;
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

}
