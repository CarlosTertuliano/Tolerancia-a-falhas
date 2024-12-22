package com.ft.store.controllers;

import com.ft.store.fault.ErrorFailure;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sell")
public class StoreController {

    @Autowired
    private ErrorFailure errorFailure;

    @PostMapping("")
    public ResponseEntity<Integer> sellProduct(@RequestParam Integer id) {

        if(errorFailure.shouldFail(0.1)) {
            System.out.println("\nStore failed! - SERVER FAILURE - internal server error \n");
            return ResponseEntity.internalServerError().build();
        }

        System.out.println("\nVenda realizada com sucesso!\n");
        return ResponseEntity.ok().body(Math.round((float) Math.random()));
    }
}
