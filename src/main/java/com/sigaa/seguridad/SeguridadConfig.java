package com.sigaa.seguridad;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SeguridadConfig implements WebMvcConfigurer {

    private final AuditoriaInterceptor auditoriaInterceptor;

    public SeguridadConfig(AuditoriaInterceptor auditoriaInterceptor) {
        this.auditoriaInterceptor = auditoriaInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(auditoriaInterceptor)
                .addPathPatterns("/api/**")

                // rutas públicas que NO deben auditarse
                .excludePathPatterns("/api/auth/login")
                .excludePathPatterns("/api/auth/registrar")
                .excludePathPatterns("/api/public/**")
                .excludePathPatterns("/api/swagger-ui/**")
                .excludePathPatterns("/api/v3/api-docs/**")
                .excludePathPatterns("/api/test/**")
                .excludePathPatterns("/api/health/**");
    }
}