package com.example.api.service;

import com.example.api.entity.User;
import com.example.api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    private UsersRepository repository;

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getByUUID(UUID id) {
        return repository.getByUUID(id);
    }

    public void save(User user){
        repository.save(user);
    }

    public void update(User user) {
        User foundUser = repository.getByUUID(user.getId());
        if (foundUser != null) {
            repository.save(user);
        }
    }

    public void deleteByUUID(UUID uuid) {

        User user = repository.getByUUID(uuid);
        if (user != null) {
            repository.delete(user);
        }
    }

}
