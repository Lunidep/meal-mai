package ru.lunidep.micro.meal.users.controller;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lunidep.micro.meal.users.dto.UserDTO;
import ru.lunidep.micro.meal.users.keycloak.KeycloakUtils;
import ru.lunidep.micro.meal.users.mq.func.MessageFuncActions;
import ru.lunidep.micro.meal.utils.rest.webclient.UserWebClientBuilder;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/admin/user")
public class AdminController {

    public static final String ID_COLUMN = "id";
    private static final int CONFLICT = 409;
    private static final String USER_ROLE_NAME = "user";
    private final KeycloakUtils keycloakUtils;


    private UserWebClientBuilder userWebClientBuilder;

    private MessageFuncActions messageFuncActions;

    public AdminController(KeycloakUtils keycloakUtils, MessageFuncActions messageFuncActions, UserWebClientBuilder userWebClientBuilder) {
        this.userWebClientBuilder = userWebClientBuilder;
        this.messageFuncActions = messageFuncActions;
        this.keycloakUtils = keycloakUtils;
    }


    @PostMapping("/add")
    public ResponseEntity add(@RequestBody UserDTO userDTO) {

        if (!userDTO.getId().isBlank()) {
            return new ResponseEntity("redundant param: id MUST be empty", HttpStatus.NOT_ACCEPTABLE);
        }

        Response createdResponse = keycloakUtils.createKeycloakUser(userDTO);

        if (createdResponse.getStatus() == CONFLICT) {
            return new ResponseEntity("user or email already exists " + userDTO.getEmail(), HttpStatus.CONFLICT);
        }

        String userId = CreatedResponseUtil.getCreatedId(createdResponse);

        System.out.printf("User.java created with userId: %s%n", userId);

        List<String> defaultRoles = new ArrayList<>();
        defaultRoles.add(USER_ROLE_NAME);
        defaultRoles.add("admin");

        keycloakUtils.addRoles(userId, defaultRoles);

        return ResponseEntity.status(createdResponse.getStatus()).build();

    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {

        keycloakUtils.updateKeycloakUser(userDTO);

        return new ResponseEntity(HttpStatus.OK);

    }


    @PostMapping("/deletebyid")
    public ResponseEntity deleteByUserId(@RequestBody String userId) {

        keycloakUtils.deleteKeycloakUser(userId);

        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping("/id")
    public ResponseEntity<UserRepresentation> findById(@RequestBody String userId) {
        return ResponseEntity.ok(keycloakUtils.findUserById(userId));


    }

    @PostMapping("/search")
    public ResponseEntity<List<UserRepresentation>> search(@RequestBody String email) {
        return ResponseEntity.ok(keycloakUtils.searchKeycloakUsers(email));

    }

    @GetMapping("/orders/{cafeId}")
    public List<OrderDTO> getAllOrdersByCafeId(@PathVariable UUID cafeId) {
        return adminService.getAllOrdersByCafeId(cafeId);
    }

    @PostMapping("/meals")
    public void adminAddMeal(@RequestBody MealDTO mealDTO) {
        adminService.adminAddMeal(mealDTO);
    }

}
