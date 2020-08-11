package com.contral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author tangxiyuan
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FileCenterApp {

    public static void main(String[] args) {
        SpringApplication.run(FileCenterApp.class, args);
    }
}
