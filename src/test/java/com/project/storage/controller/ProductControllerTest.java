package com.project.storage.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCriarEBuscarProduto() throws Exception{
        String json = """
            {
                "name": "Coca Cola",
                "brand": "Coca Cola",
                "price": 6.99,
                "cost": 4.37,
                "barCode": "123456789010"
            }
        """;

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Coca Cola")
        );

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Coca Cola"))
                .andExpect(jsonPath("$[0].brand").value("Coca Cola"))
                .andExpect(jsonPath("$[0].price").value(6.99))
                .andExpect(jsonPath("$[0].cost").value(4.37));
    }
}
