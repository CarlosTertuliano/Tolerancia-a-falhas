package com.ft.ecommerce.service;

import com.ft.ecommerce.domain.BonusRequest;
import com.ft.ecommerce.domain.Product;
import com.ft.ecommerce.failed_requests_logger.FailedRequests;
import com.ft.ecommerce.helpers.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class EcommerceService {
    private static final Logger logger = LoggerFactory.getLogger(EcommerceService.class);

    @Autowired
    private FailedRequests failedRequests;

    private static Double lastValueResponseExchange;

    private static int requestCount = 0;

    // Atributo para ativar/desativar a tolerância a falhas
    private boolean ft;

    // Construtor para inicializar o valor de ft
    public EcommerceService(boolean ft) {
        this.ft = ft;
    }

    public EcommerceService() {
    }

    public Product getProduct(int idProduct) {
        WebClient webClient = WebClient.create();

        try {
            Mono<Product> response = webClient.get()
                    .uri("http://store:8080/product?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Product.class);

            if (ft) {
                // Tolerância a falhas ativada: retry e circuit breaker
                response = response.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))); // Retry até 3 vezes
                return Helper.CircuitBreaker.run(response::block);
            } else {
                // Sem tolerância a falhas
                return response.block();
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter produto: " + e.getMessage());
            return null; // Valor padrão
        }
    }

    public Integer sellProduct(int idProduct) {
        WebClient webClient = WebClient.create();

        try {
            Mono<Integer> response = webClient.post()
                    .uri("http://store:8080/sell?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Integer.class);

            if (ft) {
                // Tolerância a falhas ativada
                response = response.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
                return Helper.CircuitBreaker.run(response::block);
            } else {
                // Sem tolerância a falhas
                return response.block();
            }
        } catch (Exception e) {
            System.err.println("Erro ao vender produto: " + e.getMessage());
            return -1; // Valor de fallback
        }
    }

    public Double getExchange() {
        WebClient webClient = WebClient.create();

        requestCount++;

        try {
            Mono<Double> response = webClient.get()
                    .uri("http://exchange" + (requestCount % 2) + ":8080/exchange")
                    .retrieve()
                    .bodyToMono(Double.class);

            if (ft) {
                response = response.doOnNext(value -> lastValueResponseExchange = value);
            }

            requestCount = requestCount % 2;
            return response.block();
        } catch (WebClientResponseException e) {
            System.err.println("Erro na requisição: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            if (ft) {
                return lastValueResponseExchange; // Fallback
            }
            return 0.0;
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            if (ft) {
                return lastValueResponseExchange; // Fallback
            }
            return 0.0;
        }
    }

    public Boolean applyBonusToUser(int idUser, Double originalValue) {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://fidelity:8080")
                .build();

        BonusRequest bonusRequest = new BonusRequest(idUser, originalValue.intValue());

        try {
            Mono<String> response = webClient.post()
                    .uri("/bonus")
                    .bodyValue(bonusRequest)
                    .retrieve()
                    .bodyToMono(String.class);

            response.block();
            return true;
        } catch (Exception e) {
            logger.error("Falha ao processar bônus para o usuário {}: {}", idUser, e.getMessage());
            if (ft) {
                failedRequests.add(bonusRequest);
            }
            return false;
        }
    }

    // Getter e Setter para o atributo ft
    public boolean isFt() {
        return ft;
    }

    public void setFt(boolean ft) {
        this.ft = ft;
    }
}

