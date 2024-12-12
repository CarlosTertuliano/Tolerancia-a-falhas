package com.ft.ecommerce.controllers;

import com.ft.ecommerce.domain.BuyRequest;
import com.ft.ecommerce.domain.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/buy")
public class EcommerceController {

    static final private String URL = "http://localhost:8080";

    @PostMapping("")
    public void buy(@RequestBody BuyRequest request) {
        //to-do adicionar verificação de falha.

        // chamada da API (/product)
        Product product = getProduct(request.getIdProduct());

        // chamada da API (/exchange)
        Double exchange = getExchange();
        product.setValue(product.getValue() * exchange);

        // chamada da API (/sell)
        Integer sellId = sellProduct();

        // chamada da API (/bonus)

    }

    private Product getProduct(int idProduct){
        WebClient webClient = WebClient.create();

        Mono<Product> response = webClient.get()
                .uri(URL + "/product?id=" + idProduct)
                .retrieve()
                .bodyToMono(Product.class);

        return response.block();
    }

    private Double getExchange(){
        WebClient webClient = WebClient.create();

        Mono<Double> response = webClient.get()
                .uri(URL + "/exchange")
                .retrieve()
                .bodyToMono(Double.class);

        return response.block();
    }

    private Integer sellProduct(){
        WebClient webClient = WebClient.create();

        Mono<Integer> response = webClient.get()
                .uri(URL + "/sell")
                .retrieve()
                .bodyToMono(Integer.class);

        return response.block();
    }
}
