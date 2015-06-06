package com.bs.twitterminer.eurekaserver;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class EurekaServerApp {

    @RequestMapping("/healthCheck")
    public String healthCheck() {
        DiscoveryClient client = DiscoveryManager.getInstance().getDiscoveryClient();
        client.getInstanceRemoteStatus();
        return "Hello world";
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApp.class, args);
    }
}
