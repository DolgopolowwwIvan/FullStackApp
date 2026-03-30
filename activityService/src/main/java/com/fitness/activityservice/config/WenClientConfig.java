package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WenClientConfig {
    // Этот класс нужен для создания
    // WebClientBuilder, с помощью которого мы будем вызывать userService
    @Bean
    @LoadBalanced // Позволяет использовать имена сервисов (логические имена) вместо реальных URL
    public WebClient.Builder webClientBuilder (){
        return WebClient.builder();
    }

    //
    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    }
}
