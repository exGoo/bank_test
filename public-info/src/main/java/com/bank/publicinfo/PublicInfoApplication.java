package com.bank.publicinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@EnableAspectJAutoProxy
@SpringBootApplication
public class PublicInfoApplication {
    public static void main(String[] args) {

        SpringApplication.run(PublicInfoApplication.class, args);

    }
}
