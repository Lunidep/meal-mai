package ru.lunidep.micro.meal.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID> {
    List<Meal> findByCafeId(UUID cafeId);
    MealInfo findByMealId(UUID mealId);
}