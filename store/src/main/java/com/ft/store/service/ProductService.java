package com.ft.store.service;

import com.ft.store.domain.Product;
import com.ft.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    public Product findProduct(int id) {
        return productRepository.findById(id).orElse(null);
    }
}
