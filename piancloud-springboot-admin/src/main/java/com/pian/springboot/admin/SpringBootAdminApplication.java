package com.pian.springboot.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 监控服务启动类
 * @author: chendong
 * @create: 2020/11/20 10:52
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class SpringBootAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class,args);
    }
}
