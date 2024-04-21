package ru.lunidep.micro.meal.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    private UserRepository userRepository;

    public void addMealToUserCartById(UUID userId, UUID mealId, int quantity) {
        userRepository.getById(userId).getCart()
                .computeIfAbsent(userId, k -> new ConcurrentHashMap<>())
                .merge(mealId, quantity, Integer::sum);
    }

    @PostMapping("/{userId}/addMeal/{mealId}")
    public ResponseEntity<Void> addMealToUserCartById(@PathVariable UUID userId, @PathVariable UUID mealId,
                                                      @RequestParam int quantity) {
        cartService.addMealToUserCartById(userId, mealId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/submitForDelivery")
    public ResponseEntity<Void> submitCartUserByIdForDelivery(@PathVariable UUID userId) {
        cartService.submitCartUserByIdForDelivery(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/submitForPickup")
    public ResponseEntity<Void> submitCartUserByIdForPickup(@PathVariable UUID userId) {
        cartService.submitCartUserByIdForPickup(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}