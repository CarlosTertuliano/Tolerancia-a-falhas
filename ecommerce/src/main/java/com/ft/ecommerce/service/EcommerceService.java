package com.ft.ecommerce.service;

import com.ft.ecommerce.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EcommerceService {

    static final private String URL = "http://localhost:8080";

    public Product getProduct(int idProduct){
        WebClient webClient = WebClient.create();

        Mono<Product> response = webClient.get()
                .uri(URL + "/product?id=" + idProduct)
                .retrieve()
                .bodyToMono(Product.class);

        return response.block();
    }

    public Double getExchange(){
        WebClient webClient = WebClient.create();

        Mono<Double> response = webClient.get()
                .uri(URL + "/exchange")
                .retrieve()
                .bodyToMono(Double.class);

        return response.block();
    }

    public Integer sellProduct(){
        WebClient webClient = WebClient.create();

        Mono<Integer> response = webClient.get()
                .uri(URL + "/sell")
                .retrieve()
                .bodyToMono(Integer.class);

        return response.block();
    }

}
