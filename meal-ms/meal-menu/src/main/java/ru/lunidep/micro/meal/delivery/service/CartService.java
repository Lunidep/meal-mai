package ru.lunidep.micro.meal.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    private KafkaTemplate<String, Map<UUID, Integer>> kafkaTemplate;

    public void submitCartUserByIdForDelivery(UUID userId, Map<UUID, Integer> cart) {
        kafkaTemplate.send("deliveryTopic", userId, cart);
    }

    public void submitCartUserByIdForPickup(UUID userId, Map<UUID, Integer> cart) {
        kafkaTemplate.send("pickupTopic", userId, cart);
    }
}
