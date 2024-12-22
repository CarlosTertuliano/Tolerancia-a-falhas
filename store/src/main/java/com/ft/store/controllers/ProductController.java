package com.ft.store.controllers;

import com.ft.store.domain.Product;
import com.ft.store.fault.OmissionFailure;
import com.ft.store.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final OmissionFailure omissionFailure;

    private Boolean allwaysFail = false;

    public ProductController(ProductService productService, OmissionFailure omissionFailure) {
        this.productService = productService;
        this.omissionFailure = omissionFailure;
    }

    @GetMapping("")
    public Product getProduct(@RequestParam int id) throws InterruptedException {

        if(omissionFailure.shouldFail(0.2) || allwaysFail){
            System.out.println("\nStore failed! - OMISSION FAILURE - sleep 10s \n");
            omissionFailure.applyCrash();
        }

        System.out.println("\nBuscando produto com id: " + id + "\n");
        return productService.findProduct(id);
    }

    @PostMapping("")
    public Boolean setAllwaysFail(@RequestParam Boolean allwaysFail) {
        this.allwaysFail = allwaysFail;
        return allwaysFail;
    }
}
