package com.yo.minimal.rest;

import com.yo.minimal.rest.constants.Constants;
import com.yo.minimal.rest.models.services.interfaces.IUploadFilePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringBootBackendApiRestApplication implements CommandLineRunner {

    @Autowired
    IUploadFilePhoto iUploadFilePhoto;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBackendApiRestApplication.class, args);

    }

    public void run(String... arg0) {
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(Constants.URL_ORIGIN)
                        .allowedOrigins(Constants.URL_ORIGIN_LOCAL)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
            }
        };
    }
}