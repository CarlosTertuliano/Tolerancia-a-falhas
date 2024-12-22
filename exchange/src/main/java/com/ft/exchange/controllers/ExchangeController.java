package com.ft.exchange.controllers;

import com.ft.exchange.fault.CrashFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private CrashFailure crashFailure;

    private Boolean allwaysFail = false;

    @GetMapping("")
    public Double getExchange() {

        if(crashFailure.shouldFail(0.1) || allwaysFail) {
            System.out.println("\nExchange failed! - CRASH FAILURE - desligando exchange \n");
            crashFailure.applyCrash();
        }

        System.out.println("\nExchange funcionou\n");
        return Math.random();
    }

    @PostMapping("")
    public Boolean setAllwaysFail(@RequestParam Boolean allwaysFail) {
        this.allwaysFail = allwaysFail;
        return allwaysFail;
    }
}
