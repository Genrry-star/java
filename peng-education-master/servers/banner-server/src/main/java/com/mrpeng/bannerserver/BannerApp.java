package com.mrpeng.bannerserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.mrpeng")
@MapperScan("com.mrpeng.bannerserver.mapper")
public class BannerApp {
    public static void main(String[] args) {
        SpringApplication.run(BannerApp.class,args);
    }
}
