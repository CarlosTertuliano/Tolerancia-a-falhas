package com.ft.ecommerce.service;

import com.ft.ecommerce.domain.BonusRequest;
import com.ft.ecommerce.domain.Product;
import com.ft.ecommerce.failed_requests_logger.FailedRequests;
import com.ft.ecommerce.helpers.Helper;
import io.netty.handler.timeout.ReadTimeoutException;
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

    private final WebClient webClient;

    private static Double lastValueResponseExchange;

    private static int requestCount = 0;

    private static boolean ft;

    public EcommerceService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Product getProduct(int idProduct) {

        try {
            Mono<Product> response = webClient.get()
                    .uri("http://store:8080/product?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Product.class);

            if (ft) {
                response = response.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))); // Retry até 3 vezes
                return Helper.CircuitBreaker.run(response::block);
            } else {
                return response.block();
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter produto: " + e.getMessage());
            if (ft) {
                Helper.CircuitBreaker.resetAfter(Duration.ofSeconds(5));
            }
            return null;
        }
    }

    public Integer sellProduct(int idProduct) {

        try {
            Mono<Integer> response = webClient.post()
                    .uri("http://store:8080/sell?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Integer.class);

            if (ft) {
                response = response.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)));
                return Helper.CircuitBreaker.run(response::block);
            } else {
                return response.block();
            }
        } catch (Exception e) {
            System.err.println("Erro ao vender produto: " + e.getMessage());
            if (ft) {
                Helper.CircuitBreaker.resetAfter(Duration.ofSeconds(5));
            }
            return -1;
        }
    }

    public Double getExchange() {
        requestCount++;

        try {
            Mono<Double> response = webClient.get()
                    .uri("http://exchange" + (requestCount % 2) + ":8080/exchange")
                    .retrieve()
                    .bodyToMono(Double.class);

            if (ft) {
                response = response.doOnNext(value -> lastValueResponseExchange = value);
            } else {
                lastValueResponseExchange = 0.0;
            }

            requestCount = requestCount % 2;
            return response.block();
        } catch (WebClientResponseException e) {
            System.err.println("Erro na requisição: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            if (ft) {
                return lastValueResponseExchange;
            }
            return 0.0;
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            if (ft) {
                return lastValueResponseExchange;
            }
            return 0.0;
        }
    }

    public Boolean applyBonusToUser(int idUser, Double originalValue) throws ReadTimeoutException {
        BonusRequest bonusRequest = new BonusRequest(idUser, originalValue.intValue());

            Mono<String> response = webClient.post()
                    .uri("http://fidelity:8080/bonus")
                    .bodyValue(bonusRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(1))
                    .doOnError(e -> {
                        logger.error("Erro ao processar bônus para o usuário {}: {}", idUser, "");
                        if (ft) {
                            failedRequests.add(bonusRequest);
                        }

                        throw new ReadTimeoutException();
                    });

            System.out.println(response.block());
            return true;

    }

    public boolean isFt() {
        return ft;
    }

    public void setFt(boolean ft) {
        this.ft = ft;
    }
}

