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
@Table(name = "meals")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;

    @Column(name = "price")
    private int price;

    @Column(name = "meal_info_id")
    private UUID mealInfoId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meals_to_cafes",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "cafe_id"))
    private Set<Cafe> cafes = new HashSet<>();
}