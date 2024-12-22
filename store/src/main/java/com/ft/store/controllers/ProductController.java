package com.ft.store.controllers;

import com.ft.store.domain.Product;
import com.ft.store.fault.OmissionFailure;
import com.ft.store.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final OmissionFailure omissionFailure;

    public ProductController(ProductService productService, OmissionFailure omissionFailure) {
        this.productService = productService;
        this.omissionFailure = omissionFailure;
    }

    @GetMapping("")
    public Product getProduct(@RequestParam int id) throws InterruptedException {

        if(omissionFailure.shouldFail(0.2)){
            System.out.println("\nStore failed! - OMISSION FAILURE - sleep 10s \n");
            omissionFailure.applyCrash();
        }

        System.out.println("\nBuscando produto com id: " + id + "\n");
        return productService.findProduct(id);
    }
}
