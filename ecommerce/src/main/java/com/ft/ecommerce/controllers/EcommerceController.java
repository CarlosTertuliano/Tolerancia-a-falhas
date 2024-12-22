package com.ft.ecommerce.controllers;

import com.ft.ecommerce.domain.BuyRequest;
import com.ft.ecommerce.domain.Product;
import com.ft.ecommerce.service.EcommerceService;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buy")
public class EcommerceController {

    @Autowired
    private EcommerceService ecommerceService;

    @PostMapping("")
    public ResponseEntity<String> buy(@RequestBody BuyRequest request) {

        ecommerceService.setFt(request.isFt());

        // chamada da API (/product)
        Product product = ecommerceService.getProduct(request.getIdProduct());

        if(product != null) {
            Double originalValue = product.getValue();

            // chamada da API (/exchange)
            Double exchange = ecommerceService.getExchange();
            System.out.println("\n Taxa de conversão: " + exchange);


            if(exchange != null && exchange != 0.0) {
                product.setValue(product.getValue() * exchange);
                System.out.println("\n Valor original: " + originalValue + "\nNovo valor do produto: " + product.getValue() + "\n");

                // chamada da API (/sell)
                Integer sellId = ecommerceService.sellProduct(request.getIdProduct());
                System.out.println("\nID da venda: " + sellId + "\n");

                if(sellId >= 0) {
                    // chamada da API (/bonus)

                    try {
                        boolean registeredBonus = ecommerceService.applyBonusToUser(request.getIdUsuario(), originalValue);
                    }
                    catch (ReadTimeoutException e) {
                        System.out.println("\nProcessamento do fidelity falhou!\n");
                    }

                    return ResponseEntity.ok("Compra realiazada com sucesso ID da venda: " +sellId + " Valor final: " + product.getValue());
                }

                return ResponseEntity.internalServerError().body("Erro ao realizar compra! Tente novamente mais tarde!");
            }

            return ResponseEntity.internalServerError().body("Erro ao buscar a taxa de conversão! Tente novamente mais tarde!");
        } else {
            return ResponseEntity.internalServerError().body("Erro ao encontrar o produto! Tente novamente mais tarde!");
        }
    }

}
