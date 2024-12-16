package com.ft.store.controllers;

import com.ft.store.domain.Product;
import com.ft.store.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public Product getProduct(@RequestParam int id) {

        //to-do Verificar se vai falhar

        return productService.findProduct(id);
    }
}
