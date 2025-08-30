package com.project.storage.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAndFindProduct() throws Exception{
        String json = """
            {
                "name": "Coca Cola",
                "brand": "Coca Cola",
                "price": 6.99,
                "cost": 4.37,
                "barCode": "123456789010"
            }
        """;

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Coca Cola")
        );

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Coca Cola"))
                .andExpect(jsonPath("$[0].brand").value("Coca Cola"))
                .andExpect(jsonPath("$[0].price").value(6.99));
    }

    @Test
    void shouldFindProductById() throws Exception{
        String json = """
            {
                "name": "Coca Cola",
                "brand": "Coca Cola",
                "price": 6.99,
                "cost": 4.37,
                "barCode": "123456789010"
            }
        """;

        String response = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Integer id = JsonPath.read(response, "$.id");

        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Coca Cola"))
                .andExpect(jsonPath("brand").value("Coca Cola"))
                .andExpect(jsonPath("price").value(6.99));
    }

    @Test
    void shouldNotFindProduct() throws Exception{
        mockMvc.perform(get("/products/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteProduct() throws Exception{
        String json = """
            {
                "name": "Coca Cola",
                "brand": "Coca Cola",
                "price": 6.99,
                "cost": 4.37,
                "barCode": "123456789010"
            }
        """;

        String response = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        Integer id = JsonPath.read(response, "$.id");

        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isNoContent());
    }
}
