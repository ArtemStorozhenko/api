package com.example.api.service;

import com.example.api.entity.Product;
import com.example.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;

    @Override
    public List<Product> getByUserId(UUID uuid) {
        return repository.getByUserId(uuid);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }
}
