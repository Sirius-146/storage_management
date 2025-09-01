package com.project.storage.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.storage.dto.ProductRequestDTO;
import com.project.storage.dto.ProductResponseDTO;
import com.project.storage.helper.ResponseEntityUtils;
import com.project.storage.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Gerenciamento de produtos")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN', 'STORAGE_MANAGER', 'MAITRE', 'WAITER')")
    @Operation(summary = "List all products", description = "Return a list with all products registered", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        Map<String, String> headers = Map.of("X-Total-Count", String.valueOf(products.size()));
        return ResponseEntityUtils.fromList(products, headers);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STORAGE_MANAGER', 'MAITRE', 'WAITER')")
    @Operation(summary = "Search product by ID", description = "Return a product by its identifier", security = { @SecurityRequirement(name = "bearerAuth") })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntityUtils.fromOptional(productService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STORAGE_MANAGER')")
    @Operation(summary = "Create products", description = "Register a product in the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {
        ProductResponseDTO created = productService.create(dto);
        URI location = URI.create("/products/" + created.id()); // ou barcode
        return ResponseEntityUtils.created(location, created);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STORAGE_MANAGER')")
    @Operation(summary = "Update products", description = "Update all the values of a product in the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Integer id, @RequestBody ProductRequestDTO dto){
        ProductResponseDTO updated = productService.update(id, dto);
        return ResponseEntityUtils.withStatus(HttpStatus.OK, updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STORAGE_MANAGER')")
    @Operation(summary = "Update products", description = "Update the chosen values of a product in the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> patch(@PathVariable Integer id, @RequestBody ProductRequestDTO dto){
        ProductResponseDTO updated = productService.patch(id, dto);
        return ResponseEntityUtils.withStatus(HttpStatus.OK, updated);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STORAGE_MANAGER')")
    @Operation(summary = "Delete product", description = "Delete a product from the database", security = { @SecurityRequirement(name = "bearerAuth") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntityUtils.deleted();
    }
}
