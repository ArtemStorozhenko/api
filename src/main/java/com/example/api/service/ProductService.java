package com.example.api.service;

import com.example.api.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getByUserId(UUID uuid);

    List<Product> findAll();
}
