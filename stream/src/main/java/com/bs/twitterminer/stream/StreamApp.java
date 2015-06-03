package com.bs.twitterminer.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StreamApp {

    public static void main(String[] args) {
        SpringApplication.run(StreamApp.class, args);
    }
}
