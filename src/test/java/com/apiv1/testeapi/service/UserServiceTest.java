package com.apiv1.testeapi.service;

import com.apiv1.testeapi.entities.User;
import com.apiv1.testeapi.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    public static final User ELYSON = new User(1, "Elyson", "elyson@gmail.com", "123");
    public static final Optional<User> VINICIUS = Optional.of(new User(1, "Vinicius", "vinicius@gmail.com", "123"));
    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private Optional<User> optionalUser;

    private User user;

    UserServiceTest() {
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void findAll() {

    }

    @Test
    void find() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        User response = service.find(ELYSON.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ELYSON.getId(), response.getId());
        Assertions.assertEquals(ELYSON.getName(), response.getName());
        Assertions.assertEquals(ELYSON.getEmail(), response.getEmail());
        Assertions.assertEquals(ELYSON.getPassword(), response.getPassword());
    }

    @Test
    void deleteId() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void att() {
    }

    @Test
    void findEmail() {
    }

    private void startUser(){
        user = ELYSON;
        optionalUser = VINICIUS;
    }
}