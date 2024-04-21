package ru.lunidep.micro.meal.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class CafeService {
    @Autowired
    private CafeRepository cafeRepository;

    public List<Cafe> getAllCafes() {
        return cafeRepository.findAll();
    }

    public Cafe getCafeById(UUID id) {
        return cafeRepository.findById(id).orElse(null);
    }

    public Cafe createCafe(Cafe cafe) {
        return cafeRepository.save(cafe);
    }

    public Cafe updateCafe(UUID id, Cafe newCafeData) {
        newCafeData.setId(id);
        return cafeRepository.save(newCafeData);
    }

    public void deleteCafe(UUID id) {
        cafeRepository.deleteById(id);
    }
}
