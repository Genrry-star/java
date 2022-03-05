package com.mrpeng.eduserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.mrpeng.eduserver.mapper")
@ComponentScan(value = "com.mrpeng") //添加需要扫描的包名
@EnableFeignClients
public class EduApp {
    public static void main(String[] args) {
        SpringApplication.run(EduApp.class,args);
    }
}
