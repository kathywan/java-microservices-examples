package com.example.apigateway;

import lombok.Data;

import java.time.LocalDate;

@Data
class Car {
    private String name;
    private LocalDate releaseDate;
}
