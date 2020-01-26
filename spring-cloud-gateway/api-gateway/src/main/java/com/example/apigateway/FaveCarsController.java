package com.example.apigateway;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
class FaveCarsController {

    private final WebClient.Builder carClient;

    public FaveCarsController(@Qualifier("loadBalancedWebClientBuilder") WebClient.Builder carClient) {
        this.carClient = carClient;
    }

    @GetMapping("/fave-cars")
    public Flux<Car> faveCars() {
        return carClient.build().get().uri("lb://car-service/cars")
                .retrieve().bodyToFlux(Car.class)
                .filter(this::isFavorite);
    }

    private boolean isFavorite(Car car) {
        return car.getName().equals("ID. BUZZ");
    }
}
