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

    private static Double lastValueResponseExchange ;

    private static int requestCount = 0;

    public Product getProduct(int idProduct) {
        WebClient webClient = WebClient.create();

        try {
            // WebClient com Retry: Tenta novamente até 3 vezes com um intervalo de 2 segundos
            Mono<Product> response = webClient.get()
                    .uri("http://store:8080/product?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))); // Retry até 3 vezes com 2 segundos de intervalo

            // Circuit Breaker: Verifica se o circuito está aberto antes de fazer a requisição
            return Helper.CircuitBreaker.run(response::block); // Retorna o resultado ou falha se o circuito estiver aberto
        } catch (Exception e) {
            // Se a requisição falhar, retorna um valor padrão ou null
            System.err.println("Erro ao obter produto: " + e.getMessage());
            return null; // Pode ser alterado para retornar um Product com valores default
        }
    }

    public Integer sellProduct(int idProduct) {
        WebClient webClient = WebClient.create();

        try {
            // WebClient com Retry: Tenta novamente até 3 vezes com um intervalo de 2 segundos
            Mono<Integer> response = webClient.post()
                    .uri("http://store:8080/sell?id=" + idProduct)
                    .retrieve()
                    .bodyToMono(Integer.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))); // Retry até 3 vezes com 2 segundos de intervalo

            // Circuit Breaker: Verifica o estado do circuito antes de tentar a requisição
            return Helper.CircuitBreaker.run(response::block); // Retorna a resposta ou falha se o circuito estiver aberto
        } catch (Exception e) {
            // Se falhar, retorna um valor de fallback (por exemplo, -1)
            System.err.println("Erro ao vender produto: " + e.getMessage());
            return -1; // Retorno de fallback indicando falha
        }
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
