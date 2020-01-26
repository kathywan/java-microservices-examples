package com.example.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
class CarsFallback {

    @GetMapping("/cars-fallback")
    public Flux<Car> noCars() {
        return Flux.empty();
    }
}
