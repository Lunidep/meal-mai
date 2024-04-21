package ru.lunidep.micro.meal.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meals")
public class MealController {
    @Autowired
    private MealService mealService;

    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> meals = mealService.getAllMeals();
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable UUID id) {
        Meal meal = mealService.getMealById(id);
        if (meal != null) {
            return new ResponseEntity<>(meal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal createdMeal = mealService.createMeal(meal);
        return new ResponseEntity<>(createdMeal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meal> updateMeal(@PathVariable UUID id, @RequestBody Meal meal) {
        Meal updatedMeal = mealService.updateMeal(id, meal);
        return new ResponseEntity<>(updatedMeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable UUID id) {
        mealService.deleteMeal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/byCafe/{cafeId}")
    public ResponseEntity<List<Meal>> getAllMealsByCafeId(@PathVariable UUID cafeId) {
        List<Meal> meals = mealService.getAllMealsByCafeId(cafeId);
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("info/{mealId}")
    public ResponseEntity<MealInfo> getMealInfoByMealId(@PathVariable UUID mealId) {
        MealInfo mealInfo = mealInfoService.getMealInfoByMealId(mealId);
        if (mealInfo != null) {
            return new ResponseEntity<>(mealInfo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}