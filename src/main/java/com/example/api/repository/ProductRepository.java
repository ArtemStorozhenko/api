package com.example.api.repository;

import com.example.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product p right join products_users pu on p.product_id = pu.product_id where pu.user_id = ?", nativeQuery = true)
    List<Product> getByUserId(UUID uuid);
}
