package com.example.IndoorTurfBookingGround;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class IndoorTurfBookingGround {
  public static void main(String[] args){
    SpringApplication.run(IndoorTurfBookingGround.class,args);
  }
}
