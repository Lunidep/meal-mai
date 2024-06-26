package ru.lunidep.micro.meal.delivery.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.lunidep.micro.meal.entity.User;

@FeignClient(name = "meal-users", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {
    @PostMapping("/user/id")
    ResponseEntity<User> findUserById(@RequestBody Long id);
}

@Component
class UserFeignClientFallback implements UserFeignClient {

    @Override
    public ResponseEntity<User> findUserById(Long id) {
        return null;
    }
}

