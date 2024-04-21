package ru.lunidep.micro.meal.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderService {
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public DeliveryOrderDTO getOrderById(UUID orderId) {
        DeliveryOrder order = deliveryOrderRepository.findById(orderId).orElse(null);
        return order != null ? mapToDTO(order) : null;
    }

    public UUID createOrder(DeliveryOrderDTO orderDTO) {
        DeliveryOrder order = mapToEntity(orderDTO);
        return deliveryOrderRepository.save(order).getId();
    }

    public void updateOrderStatus(UUID orderId, String status) {
        deliveryOrderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            deliveryOrderRepository.save(order);
        });
    }

    public void cancelOrder(UUID orderId) {
        deliveryOrderRepository.deleteById(orderId);
    }

    public List<DeliveryRouteDTO> getDeliveryRoutes() {
        return deliveryOrderRepository.findAll().stream()
                .map(this::mapToRouteDTO)
                .collect(Collectors.toList());
    }

    public String getDeliveryStatus(UUID orderId) {
        return deliveryOrderRepository.findById(orderId)
                .map(DeliveryOrder::getStatus)
                .orElse(null);
    }

    public List<DeliveryStatusHistoryDTO> getDeliveryHistory(UUID orderId) {
        return deliveryOrderRepository.findById(orderId)
                .map(order -> order.getStatusHistory().stream()
                        .map(this::mapToStatusHistoryDTO)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public double getDeliveryCost(UUID userId) {
        Map<UUID, Integer> userCart = cartService.getUserCartById(userId);

        double totalCost = 0.0;

        for (Map.Entry<UUID, Integer> entry : userCart.entrySet()) {
            UUID mealId = entry.getKey();
            int quantity = entry.getValue();

            double mealPrice = mealService.getmealPriceById(mealId);

            if (mealPrice > 0) {
                totalCost += mealPrice * quantity;
            }
        }

        return totalCost;
    }

    private DeliveryOrder mapToEntity(DeliveryOrderDTO orderDTO) {
        DeliveryOrder order = new DeliveryOrder();
        order.setId(orderDTO.getId());
        order.setOrderId(orderDTO.getOrderId());
        order.setStatus(orderDTO.getStatus());
        return order;
    }

    private DeliveryRouteDTO mapToRouteDTO(DeliveryOrder order) {
        DeliveryRouteDTO routeDTO = new DeliveryRouteDTO();
        routeDTO.setId(order.getId());
        routeDTO.setOrderId(order.getOrderId());
        return routeDTO;
    }


    private DeliveryStatusHistoryDTO mapToStatusHistoryDTO(DeliveryStatusHistory statusHistory) {
        DeliveryStatusHistoryDTO historyDTO = new DeliveryStatusHistoryDTO();
        historyDTO.setId(statusHistory.getId());
        historyDTO.setStatus(statusHistory.getStatus());
        historyDTO.setTimestamp(statusHistory.getTimestamp());
        return historyDTO;
    }
}
