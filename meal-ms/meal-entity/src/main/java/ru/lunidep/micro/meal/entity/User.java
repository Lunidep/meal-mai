package ru.lunidep.micro.meal.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;

    @Column(name = "telegram")
    private String telegram;

    @Column(name = "bonus_balance")
    private int bonusBalance;

    @Transient
    private Map<UUID, Integer> cart = new HashMap<>();

    @Transient
    private ScheduledExecutorService scheduler;

    public void scheduleCartClearing() {
        scheduler.scheduleAtFixedRate(this::clearCart, 0, 3, TimeUnit.HOURS);
    }

    private void clearCart() {
        cart.clear();
    }
}