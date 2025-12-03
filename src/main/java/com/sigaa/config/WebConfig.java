package com.sigaa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // No usar "/**" porque solapa con los endpoints "/api/**"
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}