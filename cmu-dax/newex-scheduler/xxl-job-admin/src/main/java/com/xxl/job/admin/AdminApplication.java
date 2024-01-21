package com.xxl.job.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 启动
 * @author minhan
 */
@SpringBootApplication
@EnableEurekaClient
@EnableScheduling
@ComponentScan(basePackages = {"com.xxl"})
public class AdminApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
