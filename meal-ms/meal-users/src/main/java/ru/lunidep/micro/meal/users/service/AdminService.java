package ru.lunidep.micro.meal.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MealRepository mealRepository;

    public List<OrderDTO> getAllOrdersByCafeId(UUID cafeId) {
        List<Order> orders = orderRepository.findByCafeId(cafeId);
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void adminAddMeal(MealDTO mealDTO) {
        Meal meal = mapToEntity(mealDTO);
        mealRepository.save(meal);
    }

    import java.util.stream.Collectors;

    private OrderDTO mapToDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setConsumerId(order.getConsumerId());
        orderDTO.setCafeId(order.getCafeId());
        orderDTO.setCart(order.getCart());
        return orderDTO;
    }

    private Meal mapToEntity(MealDTO mealDTO) {
        Meal meal = new Meal();
        meal.setId(mealDTO.getId());
        meal.setName(mealDTO.getName());
        meal.setImg(mealDTO.getImg());
        meal.setPrice(mealDTO.getPrice());
        meal.setMealInfoId(mealDTO.getMealInfoId());
        meal.setCafeId(mealDTO.getCafeId());
        return meal;
    }

}
