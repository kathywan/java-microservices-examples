package com.example.carservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

interface CarRepository extends ReactiveMongoRepository<Car, UUID> {
}
