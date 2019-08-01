package com.example.api.service;

import com.example.api.entity.User;

import java.util.List;
import java.util.UUID;

public interface UsersService {

    List<User> getAll();

    User getByUUID(UUID id);

    void save(User user);

    void update(User user);

    void deleteByUUID(UUID uuid);
}
