package com.ft.ecommerce.service;

import com.ft.ecommerce.domain.BonusRequest;
import com.ft.ecommerce.domain.Product;
import com.ft.ecommerce.failed_requests_logger.FailedRequests;
import com.ft.ecommerce.helpers.RetryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EcommerceService {
    private static final Logger logger = LoggerFactory.getLogger(EcommerceService.class);

    @Autowired
    private FailedRequests failedRequests;
    private static Double lastValueResponseExchange ;

    private static int requestCount = 0;

      public Product getProduct(int idProduct) {
        return RetryHelper.executeWithRetry(() ->
        {
            WebClient webClient = WebClient.create();

            Mono<Product> response = webClient.get()
                    .uri("http://store:8080/product?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Product.class);

            return response.block();
        }, 3, 2000);
    }

    public Integer sellProduct(int idProduct){
        WebClient webClient = WebClient.create();

        Mono<Integer> response = webClient.post()
                .uri("http://store:8080/sell?id=" + idProduct)
                .retrieve()
                .bodyToMono(Integer.class);

        return response.block();
    }

    public Double getExchange() {
        WebClient webClient = WebClient.create();

        requestCount++;

        try {
            Mono<Double> response = webClient.get()
                    .uri("http://exchange" + (requestCount % 2) + ":8080/exchange")
                    .retrieve()
                    .bodyToMono(Double.class)
                    .doOnNext(value -> {
                        // Atualiza a variável lastValueResponseExchange em caso de sucesso
                        lastValueResponseExchange = value;
                    });

            // Bloqueia para obter o valor retornado (não recomendável em ambientes reativos, mas usado aqui para simplicidade)
            return response.block();

        } catch (WebClientResponseException e) {
            // Lida com erros de resposta HTTP
            System.err.println("Erro na requisição: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            return lastValueResponseExchange; // Retorna o último valor conhecido
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            return lastValueResponseExchange;
        }
    }

    public Boolean applyBonusToUser(int idUser, Double originalValue) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://fidelity:8080")
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
            logger.error("Falha ao processar bônus para o usuário {}: {}", idUser, e.getMessage());
            failedRequests.add(bonusRequest);
            return false;
        }
    }

}
