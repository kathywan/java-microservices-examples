package com.example.carservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
class Car {
    @Id
    private UUID id;
    private String name;
    private LocalDate releaseDate;
}
