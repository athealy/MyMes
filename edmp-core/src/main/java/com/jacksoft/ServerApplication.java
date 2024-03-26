package com.jacksoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ServerApplication {

    private final static Logger log = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args){

        log.info("开始启动核心引擎");

        SpringApplication springApplication = new SpringApplication(ServerApplication.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.run(args);

        log.info("核心引擎启动完成");

    }

}
