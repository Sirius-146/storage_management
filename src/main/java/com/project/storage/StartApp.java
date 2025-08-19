package com.project.storage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.storage.model.Product;
import com.project.storage.repository.ProductRepository;

@Component
public class StartApp implements CommandLineRunner{

    @Autowired
    private ProductRepository repository;

    @Override
    public void run(String... args) throws Exception {
        List<Product> products = repository.findByNameContaining("Guaraná");
        for (Product p : products){
            System.out.println(p);
        }
    }

    private void insertProduct(){
        Product product = new Product();
        product.setName("Guaraná Antartica");
        product.setQuantity(12);
        product.setBrand("Coca Cola");
        product.setValue(5.99);
        
        repository.save(product);

        for(Product p : repository.findAll()){
            System.out.println(p);
        }
    }
    
}
