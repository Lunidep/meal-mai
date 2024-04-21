package ru.lunidep.micro.meal.delivery.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class DeliveryOrderDTO {
    private UUID id;
    private UUID orderId;
    private int housing;
    private boolean isApply;
}