package com.sigaa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
public class ApiPrefixConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", c -> {
            RequestMapping rm = c.getAnnotation(RequestMapping.class);
            if (rm != null) {
                for (String v : rm.value()) {
                    if (v != null && v.startsWith("/api")) {
                        return false; // ya tiene /api, no añadir
                    }
                }
            }
            return true;
        });
    }
}