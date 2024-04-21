package ru.lunidep.micro.meal.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class DeliveryOrderController {
    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<DeliveryOrder>> getAllOrders() {
        List<DeliveryOrder> orders = deliveryOrderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/applydeliveryorders/{orderId}")
    public ResponseEntity<Void> applyDeliveryOrder(@PathVariable UUID orderId) {
        deliveryOrderService.applyDeliveryOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
