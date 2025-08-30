package com.project.storage.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.project.storage.dto.ProductResponseDTO;
import com.project.storage.model.Product;
import com.project.storage.repository.ProductRepository;

class ProductServiceTest {
    
    @Test
    void deveRetornarprodutoQuandoExiste(){
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

        ProductResponseDTO resultado = service.findById(1);

        assertNotNull(resultado);
        assertEquals("Coca Cola", resultado.name());
        assertEquals("Coca Cola", resultado.brand());
        assertEquals(6.99, resultado.price());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExiste() {
        ProductRepository mockRepository = mock(ProductRepository.class);
        ProductService service = new ProductService(mockRepository);

        when(mockRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(1));
    }
}
