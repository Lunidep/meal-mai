package ru.lunidep.micro.meal.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CafeRepository extends JpaRepository<Cafe, UUID> {
}