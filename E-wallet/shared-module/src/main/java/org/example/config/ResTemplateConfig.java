package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ResTemplateConfig {

    // Khai báo RestTemplate như một Bean để Spring có thể tiêm vào các service
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}