package ru.lunidep.micro.meal.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.lunidep.micro.meal.entity.Task;
import ru.lunidep.micro.meal.entity.User;
import ru.lunidep.micro.meal.users.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private KafkaTemplate<String, CourierResponse> courierResponseKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ClientResponse> clientResponseKafkaTemplate;

    @Autowired
    private OrderService orderService;
    private final UserRepository userRepository;

    private final OrderService orderService;

    private final UserRepository repository;

    public UserService(UserRepository userRepository,
            OrderService orderService,
            UserRepository repository) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.repository = repository;
    }

    public User findByEmail(String email) {
        return repository.findFirstByEmail(email);
    }

    public User add(User user) {
        return repository.save(user);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }

    public void deleteByUserEmail(String email) {
        repository.deleteByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public Page<User> findByParams(String username, String password, PageRequest paging) {
        return repository.findByParams(username, password, paging);
    }

    public List<OrderDTO> getClientOrdersById(UUID userId) {
        return orderService.getClientOrdersByUserId(userId);
    }

    public UserInfoDTO getUserInfoByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? mapToUserInfoDTO(user) : null;
    }

    public String getQrByIdOrder(UUID orderId) {
        return orderService.generateQrCode(orderId);
    }

    public void submitDelivery(UUID orderId) {
        orderService.submitDelivery(orderId);
    }

    private UserInfoDTO mapToUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setName(user.getName());
        userInfoDTO.setTelegram(user.getTelegram());
        return userInfoDTO;
    }

    public List<OrderDTO> getClientOrdersById(UUID userId) {
        return orderService.getClientOrdersByUserId(userId);
    }

    public UserInfoDTO getUserInfoByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? mapToUserInfoDTO(user) : null;
    }

    public String getQrByIdOrder(UUID orderId) {
        return orderService.generateQrCode(orderId);
    }

    public void submitDelivery(UUID orderId) {
        orderService.submitDelivery(orderId);
        ClientResponse clientResponse = new ClientResponse(orderId, "Order accepted for processing");
        clientResponseKafkaTemplate.send("client-response-topic", clientResponse);
    }

    private UserInfoDTO mapToUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setName(user.getName());
        userInfoDTO.setTelegram(user.getTelegram());
        return userInfoDTO;
    }
}
