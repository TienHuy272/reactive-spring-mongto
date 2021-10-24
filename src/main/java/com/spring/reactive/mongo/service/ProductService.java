package com.spring.reactive.mongo.service;

import com.spring.reactive.mongo.dto.ProductDTO;
import com.spring.reactive.mongo.repository.ProductRepository;
import com.spring.reactive.mongo.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDTO> getProducts() {
        return productRepository.findAll().map(AppUtils::entity2DTO);
    }

    public Mono<ProductDTO> getProduct(String id) {
        return productRepository.findById(id).map(AppUtils::entity2DTO);
    }

    public Flux<ProductDTO> getProductInRange(double min, double max) {
        return productRepository.findByPriceBetween(Range.closed(min, max));
    }

    public Mono<ProductDTO> saveProduct(Mono<ProductDTO> productDTOMono) {
        return productDTOMono.map(AppUtils::DTO2Entity)
                        .flatMap(productRepository::insert)
                        .map(AppUtils::entity2DTO);
    }

    public Mono<ProductDTO> updateProduct(Mono<ProductDTO> productDTOMono, String id) {
        return productRepository.findById(id)
                         .flatMap(p -> productDTOMono.map(AppUtils::DTO2Entity)
                         .doOnNext(e -> e.setId(id)))
                         .flatMap(productRepository::save).map(AppUtils::entity2DTO);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
