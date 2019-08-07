package com.example.api.controller;

import com.example.api.entity.Product;
import com.example.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/{uuid}")
    public List<Product> getByUserId(@PathVariable(value = "uuid") UUID uuid) {
        return service.getByUserId(uuid);
    }

    @GetMapping("/get")
    public List<Product> findAll() {
        return service.findAll();
    }
}
