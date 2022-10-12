package com.apiv1.testeapi.controller;

import com.apiv1.testeapi.entities.User;
import com.apiv1.testeapi.entities.dtos.UserDTO;
import com.apiv1.testeapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
public class UserController {


    @Autowired
    private UserService service;


    @GetMapping(value="/{id}")
    public ResponseEntity<UserDTO> findByID(@PathVariable Integer id){
        User user = service.find(id);
        UserDTO newObj = service.upUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(newObj);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<User> list = service.findAll();
        List<UserDTO> listDto = list.stream().map(obj -> new UserDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(listDto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user){
        User newObj = service.downUser(user);
        newObj = service.insert(newObj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id,@RequestBody UserDTO user){
        User user1 = service.update(id,user);
        UserDTO newObj = service.upUser(user1);
        return ResponseEntity.status(HttpStatus.OK).body(newObj);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteByID(@PathVariable Integer id){
        service.deleteId(id);
        return ResponseEntity.ok().build();
    }



}
