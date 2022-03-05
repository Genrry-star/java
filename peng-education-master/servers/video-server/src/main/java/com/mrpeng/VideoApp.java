package com.mrpeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@ComponentScan(value = "com.mrpeng")
public class VideoApp {
    public static void main(String[] args) {
        SpringApplication.run(VideoApp.class,args);
    }
}
