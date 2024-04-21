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
@Table(name = "cafes")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;

    @Column(name = "address")
    private String address;

    @ManyToMany(mappedBy = "cafes", fetch = FetchType.LAZY)
    private Set<Meal> meals = new HashSet<>();
}