package ru.lunidep.micro.meal.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MealService {
    @Autowired
    private MealRepository mealRepository;

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Meal getMealById(UUID id) {
        return mealRepository.findById(id).orElse(null);
    }

    public Meal createMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(UUID id, Meal newMealData) {
        newMealData.setId(id);
        return mealRepository.save(newMealData);
    }

    public void deleteMeal(UUID id) {
        mealRepository.deleteById(id);
    }

    public List<Meal> getAllMealsByCafeId(UUID cafeId) {
        return mealRepository.findByCafeId(cafeId);
    }

    public MealInfo getMealInfoByMealId(UUID mealId) {
        return mealInfoRepository.findByMealId(mealId);
    }
}