package com.ft.exchange.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @GetMapping("")
    public Double getExchange() {

        //to-do Verificar se vai falhar

        return Math.random();
    }
}
