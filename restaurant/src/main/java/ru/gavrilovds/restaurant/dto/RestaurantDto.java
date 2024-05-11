package ru.gavrilovds.restaurant.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.List;

@Embeddable
public record RestaurantDto(
    String name,
    @Column(length = 1000)
    List<String> images,
    String description,
    Long id
) {

}
