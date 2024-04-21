package ru.lunidep.micro.meal.users.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.lunidep.micro.meal.entity.User;
import ru.lunidep.micro.meal.users.mq.func.MessageFuncActions;
import ru.lunidep.micro.meal.users.search.UserSearchValues;
import ru.lunidep.micro.meal.users.service.UserService;
import ru.lunidep.micro.meal.utils.rest.webclient.UserWebClientBuilder;

import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    public static final String ID_COLUMN = "id";
    private final UserService userService;
    private KafkaTemplate<String, Long> kafkaTemplate;

    private UserWebClientBuilder userWebClientBuilder;

    private MessageFuncActions messageFuncActions;

    public UserController(KafkaTemplate<String, Long> kafkaTemplate, MessageFuncActions messageFuncActions, UserService userService, UserWebClientBuilder userWebClientBuilder) {
        this.userService = userService;
        this.userWebClientBuilder = userWebClientBuilder;
        this.messageFuncActions = messageFuncActions;
        this.kafkaTemplate = kafkaTemplate;
    }


    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {

        user = userService.add(user);

        if (user != null) {
            kafkaTemplate.send(TOPIC_NAME, user.getId());
        }

        return ResponseEntity.ok(user);

    }


    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {

        userService.update(user);

        return new ResponseEntity(HttpStatus.OK);

    }


    @PostMapping("/deletebyid")
    public ResponseEntity deleteByUserId(@RequestBody Long userId) {

        try {
            userService.deleteByUserId(userId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("userId=" + userId + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/deletebyemail")
    public ResponseEntity deleteByUserEmail(@RequestBody String email) {

        try {
            userService.deleteByUserEmail(email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("email=" + email + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/id")
    public ResponseEntity<User> findById(@RequestBody Long id) {

        Optional<User> userOptional = userService.findById(id);

        try {
            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);

    }


    @PostMapping("/search")
    public ResponseEntity<Page<User>> search(@RequestBody UserSearchValues userSearchValues) throws ParseException {

        String email = userSearchValues.getEmail() != null ? userSearchValues.getEmail() : null;

        String username = userSearchValues.getUsername() != null ? userSearchValues.getUsername() : null;

        String sortColumn = userSearchValues.getSortColumn() != null ? userSearchValues.getSortColumn() : null;
        String sortDirection = userSearchValues.getSortDirection() != null ? userSearchValues.getSortDirection() : null;

        Integer pageNumber = userSearchValues.getPageNumber() != null ? userSearchValues.getPageNumber() : null;
        Integer pageSize = userSearchValues.getPageSize() != null ? userSearchValues.getPageSize() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> result = userService.findByParams(email, username, pageRequest);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/{userId}/orders")
    public List<OrderDTO> getClientOrdersById(@PathVariable UUID userId) {
        return userService.getClientOrdersById(userId);
    }

    @GetMapping("/{userId}")
    public UserInfoDTO getUserInfoByUserId(@PathVariable UUID userId) {
        return userService.getUserInfoByUserId(userId);
    }

    @GetMapping("/orders/{orderId}/qr")
    public String getQrByIdOrder(@PathVariable UUID orderId) {
        return userService.getQrByIdOrder(orderId);
    }

    @PostMapping("/orders/{orderId}/delivery")
    public void submitDelivery(@PathVariable UUID orderId) {
        userService.submitDelivery(orderId);
    }

    @GetMapping("/{userId}/orders")
    public List<OrderDTO> getClientOrdersById(@PathVariable UUID userId) {
        return userService.getClientOrdersById(userId);
    }

    @GetMapping("getUserInfo/{userId}")
    public UserInfoDTO getUserInfoByUserId(@PathVariable UUID userId) {
        return userService.getUserInfoByUserId(userId);
    }

    @GetMapping("/orders/{orderId}/qr")
    public String getQrByIdOrder(@PathVariable UUID orderId) {
        return userService.getQrByIdOrder(orderId);
    }

    @PostMapping("/orders/{orderId}/delivery")
    public void submitDelivery(@PathVariable UUID orderId) {
        userService.submitDelivery(orderId);
    }

}
