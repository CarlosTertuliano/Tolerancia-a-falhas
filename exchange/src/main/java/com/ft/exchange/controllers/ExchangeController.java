package com.ft.exchange.controllers;

import com.ft.exchange.fault.CrashFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private CrashFailure crashFailure;

    @GetMapping("")
    public Double getExchange() {

        if(crashFailure.shouldFail(0.1)){
            System.out.println("\nExchange failed! - CRASH FAILURE - desligando exchange \n");
            crashFailure.applyCrash();
        }

        System.out.println("\nExchange funcionou\n");
        return Math.random();
    }
}
