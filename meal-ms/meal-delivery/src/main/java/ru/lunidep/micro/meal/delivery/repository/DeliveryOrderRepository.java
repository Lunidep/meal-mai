package ru.lunidep.micro.meal.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, UUID> {
}
