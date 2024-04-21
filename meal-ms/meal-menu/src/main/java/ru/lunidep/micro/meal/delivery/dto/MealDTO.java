package ru.lunidep.micro.meal.menu.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class MealDTO {
    private UUID id;
    private String name;
    private String img;
    private int price;
    private UUID mealInfoId;
    private UUID cafeId;
}