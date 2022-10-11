package com.apiv1.testeapi.service;

import com.apiv1.testeapi.entities.User;
import com.apiv1.testeapi.entities.dtos.UserDTO;
import com.apiv1.testeapi.repository.UserRepository;
import com.apiv1.testeapi.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User find(Integer id){
        Optional<User> user = repository.findById(id);
        return user.orElseThrow( () -> new ObjectNotFoundException("Error: Usuario não existe.") );
    }

    public void deleteId(Integer id){
        repository.deleteById(id);
    }

    public User insert(User user){
        user.setId(null);
        findEmail(user);
        return repository.save(user);
    }

    public User update(Integer id, UserDTO user){
        User obj = repository.getReferenceById(id);
        att(obj,user);
        return repository.save(obj);
    }












    public void att(User inset, UserDTO obj){
        inset.setName(obj.getName());
        inset.setEmail(obj.getEmail());
        inset.setPassword(obj.getPassword());
    }

    public void findEmail(User x){
        Optional<User> user = repository.findByEmail(x.getEmail());
        if(user.isPresent()){
            throw new DataIntegrityViolationException("Error: Email ja esta cadastrado!!");
        }

    }

    public UserDTO upUser(User user){
        return new UserDTO(user.getId(), user.getName(),user.getEmail(), user.getPassword());
    }

    public User downUser(UserDTO user){
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

}
