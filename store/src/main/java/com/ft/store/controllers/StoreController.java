package com.ft.store.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sell")
public class StoreController {

    @PostMapping("")
    public Integer sellProduct(@RequestParam Integer idProduct) {

        //to-do Verificar se vai falhar

        //to-do ver se tem um jeito melhor de fazer
        return Math.round((float) Math.random());
    }
}
