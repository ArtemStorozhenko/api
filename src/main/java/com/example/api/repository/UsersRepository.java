package com.example.api.repository;

import com.example.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users where user_id = ?1", nativeQuery = true)
    User findByUUID(UUID uuid);

    @Modifying
    @Query(value = "update users set name = ?2, created_on  = ?3, modified_on = ?4 " +
            "where user_id = ?1;", nativeQuery = true)
    void update(UUID userId, String userName, Date userCreatedOn, Date usersModifiedOn, String productName);

}
