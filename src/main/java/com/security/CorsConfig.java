package com.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO Auto-generated method stub
        super.addCorsMappings(registry);
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }
}
