package com.ft.ecommerce.controllers;

import com.ft.ecommerce.domain.BuyRequest;
import com.ft.ecommerce.domain.Product;
import com.ft.ecommerce.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/buy")
public class EcommerceController {

    static final private String URL = "http://localhost:8080";

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping("")
    public void buy(@RequestBody BuyRequest request) {
        //to-do adicionar verificação de falha.

        // chamada da API (/product)
        Product product = ecommerceService.getProduct(request.getIdProduct());
        Double originalValue = product.getValue();

        // chamada da API (/exchange)
        Double exchange = ecommerceService.getExchange();
        product.setValue(product.getValue() * exchange);

        // chamada da API (/sell)
        Integer sellId = ecommerceService.sellProduct();

        // chamada da API (/bonus)
        boolean registeredBonus = ecommerceService.applyBonusToUser(request.getIdUsuario(), originalValue);

    }

}
