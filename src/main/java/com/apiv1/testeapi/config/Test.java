package com.apiv1.testeapi.config;

import com.apiv1.testeapi.entities.User;
import com.apiv1.testeapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("local")
public class Test {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB() {
        User user = new User(null, "Elyson", "elyson@gmail.com", "123");
        User user1 = new User(null, "Vinicius", "vinicius@gmail.com", "123");
        User user2 = new User(null, "Carlos", "carlos@gmail.com", "123");
        repository.saveAll(Arrays.asList(user,user2,user1));
    }
}
