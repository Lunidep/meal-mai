package ru.lunidep.micro.meal.menu.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class CafeDTO {
    private UUID id;
    private String name;
    private String img;
    private String address;
}