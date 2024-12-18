package com.ft.ecommerce.service;

import com.ft.ecommerce.domain.BonusRequest;
import com.ft.ecommerce.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class EcommerceService {

    private static final String URL = "http://localhost:";

    private static int requestCount = 0;

    public Product getProduct(int idProduct){
        WebClient webClient = WebClient.create();

        Mono<Product> response = webClient.get()
                .uri(URL + "8081" + "/product?id=" + idProduct)
                .retrieve()
                .bodyToMono(Product.class);

        return response.block();
    }

    public Integer sellProduct(){
        WebClient webClient = WebClient.create();

        Mono<Integer> response = webClient.get()
                .uri(URL + "8081" + "/sell")
                .retrieve()
                .bodyToMono(Integer.class);

        return response.block();
    }

    public Double getExchange() {
        WebClient webClient = WebClient.create();

        requestCount++;

        Mono<Double> response = webClient.get()
                .uri(URL + "808" + ((requestCount % 2) + 2) + "/exchange")
                .retrieve()
                .bodyToMono(Double.class);

        return response.block();
    }

    public Boolean applyBonusToUser(int idUser, Double originalValue) {
        WebClient webClient = WebClient.builder()
                .baseUrl(URL + "8084")
                .build();

        BonusRequest bonusRequest = new BonusRequest(idUser, originalValue.intValue());

        try {
            String response = webClient.post()
                    .uri("/bonus")
                    .bodyValue(bonusRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
