package com.example.api;

import com.example.api.entity.Product;
import com.example.api.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.jdbc.Sql;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestUsersController {

    @Autowired
    private MockMvc mockMvc;

    private String userID;

    private final String RemoveAndCreateData = "";

    @Test
    @Sql(RemoveAndCreateData)
    public void findAllUsers() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ann")))
                .andExpect(jsonPath("$[0].products", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[1].products", hasSize(1)))
                .andReturn();
//                .andExpect(jsonPath("$[2].id", is("f3f9faaf-ec9e-4bff-9781-b5922f15f269")))
//                .andExpect(jsonPath("$[2].name", is("Max")))
//                .andExpect(jsonPath("$[2].products", hasSize(1)));

        String content = result.getResponse().getContentAsString();
        userID = JsonPath.read(content, "[1].id");

    }

    @Test
    public void getUserByUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get/{id}", userID /*"e74b86d6-b46b-11e9-986b-1fa4762759f8"*/)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andExpect(jsonPath("$.id", is("f3f9faaf-ec9e-4bff-9781-b5922f15f269")))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.products", hasSize(1)));
    }

   /* @Test
    public void createUser() throws Exception {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Product("product1", null, null));
        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8081/user/save")
                .content(new ObjectMapper().writeValueAsString(new User("Max", productSet, null, null)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }*/

    /*@Test
    public void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("http://localhost:8081/user/get/{id}", userID)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    }*/
}
