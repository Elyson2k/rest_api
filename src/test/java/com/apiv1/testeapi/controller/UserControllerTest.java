package com.apiv1.testeapi.controller;

import com.apiv1.testeapi.entities.User;
import com.apiv1.testeapi.entities.dtos.UserDTO;
import com.apiv1.testeapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserControllerTest {



    public static final int ID = 1;
    public static final String PASSWORD = "123";
    public static final User ELYSON = new User(1, "Elyson", "elyson@gmail.com", "123");
    public static final String NAME = "Vinicius";
    public static final String EMAIL = "vinicius@gmail.com";
    public static final int INDEX = 0;
    public static final UserDTO USER_DTO = new UserDTO(new User(1, "Cleitin", "cleitin@gmail.com", "123"));
    public static final Optional<User> OPTIONAL_USER = Optional.of(new User(1, "Elyson", "elyson@gmail.com", "123"));

    private User user = new User();
    private UserDTO userDTO = new UserDTO();
    private List<UserDTO> listDto;
    private List<User> listUser;
    private Optional<User> optionalUser;


    @InjectMocks
    private UserController resource;

    @Mock
    private UserService service;



    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startUser();
    }



    @Test
    void findByID() {
        when(service.find(anyInt())).thenReturn(user);
        when(service.upUser(user)).thenReturn(userDTO);

        // Chamando um ResponseEntity que tem um UserDTO como parametro para chamar a função findByID
        ResponseEntity<UserDTO> response = resource.findByID(ID);
        // Verificando se o corpo do response não retorna null.
        assertNotNull(response.getBody());
        //Verificando se o meu response.getClass retorna uma classe do tipo ResponseEntity.
        assertEquals(ResponseEntity.class, response.getClass());
        // Verificando se o corpo do meu response retorna um UserDTO.
        assertEquals(UserDTO.class, response.getBody().getClass());

        // Verificando se o ID,NAME,EMAIL e PASSWORD são iguais ao objeto que foi passado.
        assertEquals(USER_DTO.getId(), response.getBody().getId());
        assertEquals(USER_DTO.getName(), response.getBody().getName());
        assertEquals(USER_DTO.getEmail(), response.getBody().getEmail());
        assertEquals(USER_DTO.getPassword(), response.getBody().getPassword());

    }

    @Test
    void findAll() {
        when(service.findAll()).thenReturn(List.of(user));
        ResponseEntity<List<UserDTO>> response = resource.findAll();

        // Verificando se o corpo do response não retorna null.
        assertNotNull(response);
        assertNotNull(response.getBody());
        //Verificando se o meu response.getClass retorna uma classe do tipo ResponseEntity.
        assertEquals(ResponseEntity.class, response.getClass());
        // Verificando se o corpo do meu response retorna um UserDT O.
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        // Verificando se o ID,NAME,EMAIL e PASSWORD são iguais ao objeto que foi passado.
        assertEquals(ELYSON.getId(), response.getBody().get(INDEX).getId());
        assertEquals(ELYSON.getName(), response.getBody().get(INDEX).getName());
        assertEquals(ELYSON.getEmail(), response.getBody().get(INDEX).getEmail());
    }

    @Test
    void insert() {
        when(service.insert(any())).thenReturn(user);
        when(service.downUser(any())).thenReturn(user);
        ResponseEntity<UserDTO> insert = resource.insert(userDTO);
        assertEquals(HttpStatus.CREATED, insert.getStatusCode());
        assertNotNull(insert);
        assertEquals(ResponseEntity.class, insert.getClass());
    }

    @Test
    void update() {
        when(service.update(anyInt(), any())).thenReturn(user);
        when(service.upUser(any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> update = resource.update(ID,USER_DTO);
        assertNotNull(update);
        assertNotNull(update.getBody());
        assertEquals(HttpStatus.OK, update.getStatusCode());

        assertEquals(USER_DTO.getId(), update.getBody().getId());
        assertEquals(USER_DTO.getName(), update.getBody().getName());
        assertEquals(USER_DTO.getEmail(), update.getBody().getEmail());
        assertEquals(USER_DTO.getPassword(), update.getBody().getPassword());
    }

    @Test
    void deleteByID() {
        // Não faça nada / quando meu service / chamar o deleteId.
        doNothing().when(service).deleteId(anyInt());
        ResponseEntity<Void> delet = resource.deleteByID(anyInt());
        assertNotNull(delet);
        assertEquals(ResponseEntity.class, delet.getClass());

    }



    private void startUser(){
        user = ELYSON;
        userDTO = USER_DTO;
        optionalUser = OPTIONAL_USER;
    }
}