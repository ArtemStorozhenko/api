package com.example.api;

import com.example.api.entity.Product;
import com.example.api.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TestUsersController {

    @Autowired
    private MockMvc mockMvc;

    private final String RECREATE_DATA = "classpath:/recreate-data.sql";

    @Test
    @Sql(scripts = RECREATE_DATA, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findAllUsers() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ann")))
                .andExpect(jsonPath("$[0].products", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[1].products", hasSize(1)));
    }

    @Test
    @Sql(scripts = RECREATE_DATA, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getUserByUUID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get/{id}", "e74b86d6-b46b-11e9-986b-1fa4762759f8")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.products", hasSize(1)));
    }

    @Test
    @Sql(scripts = RECREATE_DATA, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void createUser() throws Exception {
        Set<Product> productSet = new HashSet<>();
        productSet.add(new Product("product1", null, null));
        mockMvc.perform(MockMvcRequestBuilders
                .post("http://localhost:8081/user/save")
                .content(new ObjectMapper().writeValueAsString(new User("Max", productSet, null, null)))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        assertEquals(countDBData(), 3);
    }

    @Test
    @Sql(scripts = RECREATE_DATA, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("http://localhost:8081/user/delete/{uuid}", "e74b86d6-b46b-11e9-986b-1fa4762759f8")
                .accept(MediaType.APPLICATION_JSON_UTF8));
        assertEquals(countDBData(), 1);
    }

    private int countDBData() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("http://localhost:8081/user/get")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        return new JSONArray(result.getResponse().getContentAsString()).length();
    }
}
