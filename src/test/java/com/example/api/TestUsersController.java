package com.example.api;

import com.example.api.entity.Product;
import com.example.api.entity.User;
import com.example.api.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUsersController {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsersService userService;

    @Test
    public void getAllUsersAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("a78e5553-4beb-4482-adad-6026a72b370b")))
                .andExpect(jsonPath("$[0].name", is("Ann")))
                .andExpect(jsonPath("$[0].products", hasSize(2)))
                .andExpect(jsonPath("$[1].id", is("f3f9faaf-ec9e-4bff-9781-b5922f15f269")))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[1].products", hasSize(0)));
    }

    @Test
    public void getUserByUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get/{id}", "a78e5553-4beb-4482-adad-6026a72b370b")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id", is("a78e5553-4beb-4482-adad-6026a72b370b")))
                .andExpect(jsonPath("$.name", is("Ann")))
                .andExpect(jsonPath("$.products", hasSize(2)));
    }

    @Test
    public void createUser() throws Exception {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Product("product1", null, null));
        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8081/user/save")
                .content(new ObjectMapper().writeValueAsString(new User("Max", productSet, null, null)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists());
    }
}
