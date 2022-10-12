package com.apiv1.testeapi.service;

import com.apiv1.testeapi.entities.User;
import com.apiv1.testeapi.entities.dtos.UserDTO;
import com.apiv1.testeapi.repository.UserRepository;
import com.apiv1.testeapi.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {


    public static final int ID = 1;
    public static final String PASSWORD = "123";
    public static final User ELYSON = new User(1, "Elyson", "elyson@gmail.com", "123");
    public static final String NAME = "Vinicius";
    public static final String EMAIL = "vinicius@gmail.com";
    public static final int INDEX = 0;
    public static final UserDTO USER_DTO = new UserDTO(new User(1, "Cleitin", "cleitin@gmail.com", "123"));

    // InectMocks serve para criar uma instancia real do service.
    @InjectMocks
    private UserService service;

    // Anotação obrigatoria.
    @Mock
    private UserRepository repository;

    private Optional<User> optionalUser;
    private List<User> listUser;
    private User user;
    private UserDTO userDTO;

    UserServiceTest() {
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void findAll() {

        // Quando meu repository chamar um findAll, retorne uma lista de user, que no caso user é um objeto que ja esta instanciado aqui na nossa classe.
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> list = service.findAll();

        // Verificando se a lista esta vazia/nulla.
        assertNotNull(list);
        // Verificando o tamanho da lista.
        assertEquals(1, list.size());
        // Verificando se o indice da pos "0" é um objeto do tipo User.
        assertEquals(User.class, list.get(INDEX).getClass());
        // Verificando se esta vindo os dados corretos, ID,NOME,EMAIL,PASSWORD.
        assertEquals(ELYSON.getId(), list.get(INDEX).getId());
        assertEquals(ELYSON.getName(), list.get(INDEX).getName());
        assertEquals(ELYSON.getEmail(), list.get(INDEX).getEmail());
        assertEquals(ELYSON.getPassword(), list.get(INDEX).getPassword());
    }

    @Test
    void find() {

        // Quando esse metodo for chamado, qualquer valor que for passado, retorne um OptionalUser.
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        // Chamando o metodo find, para testar.
        User response = service.find(ID);

        // Verificando se esta vindo os dados corretos, ID,NOME,EMAIL,PASSWORD.
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void deleteId() {
        // Quando eu chamar meu repository.findById passando qualquer nmr inteiro, retorne um optionalUser.
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        // Não faca nada enquanto meu repository deleteById recebe qualquer numero inteiro.
        doNothing().when(repository).deleteById(anyInt());

        //Deletando o id;
        service.deleteId(ID);

        //Verificando se o deleteById foi chamado mais de uma vez, se tiver sido chamado ele não passou no teste.
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void insert() {

        //Quando eu chamar meu repository e salvar qualquer coisa, retorne um objeto do tipo usuario..
        when(repository.save(any())).thenReturn(user);
        User response = service.insert(user);

        // Verificando se não retornou null.
        assertNotNull(response);

        // Verificando se esta vindo os dados corretos, ID,NOME,EMAIL,PASSWORD.
        assertEquals(User.class, response.getClass());
        assertEquals(ELYSON.getId(), response.getId());
        assertEquals(ELYSON.getName(), response.getName());
        assertEquals(ELYSON.getEmail(), response.getEmail());
        assertEquals(ELYSON.getPassword(), response.getPassword());
    }

    @Test
    void update() {
        when(repository.save(any())).thenReturn(user);
        when(repository.getReferenceById(ID)).thenReturn(user);
        User insert = repository.getReferenceById(1);
        service.att(insert, userDTO);
        insert = service.update(ID, userDTO);
        assertNotNull(insert);
    }

    @Test
    void att() {

    }





    @Test
    void findEmail() {
    }








    // ============================== TESTES DE EXCEÇÕES ====================================

    @Test
    void findByIDexception(){

        // Quando chamar um metodo findByID,     estoure uma exceção ObjectNotFoundException com essa menssagem.
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Error: Usuario não existe."));

        try{
            service.find(ID);
        } catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Error: Usuario não existe.", e.getMessage());
        }
    }



    @Test
    void findByEmailException(){
        when(repository.save(any())).thenThrow(new DataIntegrityViolationException("Error: Email ja esta cadastrado!!"));

        try{
            service.insert(user);
        } catch (Exception e){
            assertEquals(DataIntegrityViolationException.class, e.getClass());
            assertEquals("Error: Email ja esta cadastrado!!", e.getMessage());
        }
    }


    @Test
    void deleteError(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Error: Usuario não existe."));
        try{
            doNothing().when(repository).deleteById(anyInt());
            service.deleteId(ID);
        } catch (Exception e){
            assertEquals(ObjectNotFoundException.class, e.getClass());
            assertEquals("Error: Usuario não existe.", e.getMessage());
        }
    }
    @Test
    void dataIntegrityError(){
        when(repository.findByEmail(anyString())).thenThrow(new com.apiv1.testeapi.service.exceptions.DataIntegrityViolationException("Error: Email ja esta cadastrado!!"));
        try{
            service.findEmail(user);
        } catch (Exception e){
            assertEquals(com.apiv1.testeapi.service.exceptions.DataIntegrityViolationException.class, e.getClass());
            assertEquals("Error: Email ja esta cadastrado!!", e.getMessage());
        }
    }





    private void startUser(){
        user = ELYSON;
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
        userDTO = USER_DTO;
    }
}