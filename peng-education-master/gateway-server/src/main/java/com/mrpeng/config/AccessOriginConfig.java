package com.mrpeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
@Configuration
public class AccessOriginConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //允许跨域的请求头
        corsConfiguration.addAllowedHeader("*");
        //允许跨域的请求方式
        corsConfiguration.addAllowedMethod("*");
        //允许跨域的请求来源
        corsConfiguration.addAllowedOrigin("*");
        //允许携带cookie跨域
        corsConfiguration.setAllowCredentials(true);

        ///**代表 允许任意的请求路径进行跨域
        source.registerCorsConfiguration("/**",corsConfiguration);

        return new CorsWebFilter(source);
    }
}
