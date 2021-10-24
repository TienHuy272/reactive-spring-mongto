package com.spring.reactive.mongo.controller;

import com.spring.reactive.mongo.dto.ProductDTO;
import com.spring.reactive.mongo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProduct(@PathVariable String id) {
        return productService.getProduct(id);
    }

    @GetMapping("/product-range")
    public Flux<ProductDTO> productBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return productService.getProductInRange(min, max);
    }

    @PostMapping
    public Mono<ProductDTO> save(@RequestBody Mono<ProductDTO> productDTOMono) {
        return productService.saveProduct(productDTOMono);
    }

    @PutMapping("/{id}")
    public Mono<ProductDTO> update(@RequestBody Mono<ProductDTO> productDTOMono, @PathVariable String id) {
        return productService.updateProduct(productDTOMono, id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}
