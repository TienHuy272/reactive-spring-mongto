package com.spring.reactive.mongo.utils;

import com.spring.reactive.mongo.dto.ProductDTO;
import com.spring.reactive.mongo.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDTO entity2DTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product DTO2Entity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
