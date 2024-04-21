package ru.lunidep.micro.meal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "meals_info")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MealInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "bonus_price")
    private int bonusPrice;

    @Column(name = "weight")
    private double weight;

    @Column(name = "calories")
    private double calories;

    @Column(name = "structure")
    private String structure;
}