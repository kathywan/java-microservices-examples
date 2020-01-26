package com.example.apigateway;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           TokenRelayGatewayFilterFactory filterFactory) {
        return builder.routes()
                .route("car-service", r -> r.path("/cars")
                        .filters(f -> f.filter(filterFactory.apply()))
                        // .filters(f -> f.hystrix(c -> c.setName("carsFallback")
                        // .setFallbackUri("forward:/cars-fallback")))
                        .uri("lb://car-service"))
                .build();
    }

    @Bean
    @LoadBalanced
    @Qualifier("loadBalancedWebClientBuilder")
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}

