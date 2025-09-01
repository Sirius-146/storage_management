package com.project.storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.project.storage.dto.ProductResponseDTO;
import com.project.storage.model.Product;
import com.project.storage.repository.ProductRepository;

class ProductServiceTest {
    
    @Test
    void deveRetornarProdutoQuandoExiste(){
        ProductRepository mockRepository = mock(ProductRepository.class);
        ProductService service = new ProductService(mockRepository);

        Product product = new Product();
        product.setId(1);
        product.setName("Coca Cola");
        product.setBrand("Coca Cola");
        product.setPrice(6.99);
        product.setCost(4.37);
        product.setBarCode("123456789010");

        when(mockRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<ProductResponseDTO> resultado = service.findById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Coca Cola", resultado.get().name());
        assertEquals("Coca Cola", resultado.get().brand());
        assertEquals(6.99, resultado.get().price());
    }

    @Test
    void deveRetornarVazioQuandoProdutoNaoExiste() {
        ProductRepository mockRepository = mock(ProductRepository.class);
        ProductService service = new ProductService(mockRepository);

        when(mockRepository.findById(1)).thenReturn(Optional.empty());

        Optional<ProductResponseDTO> resultado = service.findById(1);

        assertTrue(resultado.isEmpty());
    }
}
