package com.example.api.controller;

import com.example.api.entity.User;
import com.example.api.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService service;

    @GetMapping(value = "/get")
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("get/{id}")
    public User getById(@PathVariable(value = "id") UUID id) {
        return service.findByUUID(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody User user){
        service.save(user);
    }

    @GetMapping("/update")
    public void update(@RequestBody User user){
        service.update(user);
    }

    @PostMapping("/modify")
    public void modifyUser(@RequestBody User user) {
        service.save(user);
    }

    @DeleteMapping("/delete/{uuid}")
    public void delete(@PathVariable(value = "uuid") UUID uuid) {
        service.deleteByUUID(uuid);
    }
}
