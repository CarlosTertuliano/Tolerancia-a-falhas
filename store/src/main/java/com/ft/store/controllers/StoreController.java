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
            ResponseEntity.internalServerError().build();
        }

        //to-do ver se tem um jeito melhor de fazer
        return ResponseEntity.ok().body(Math.round((float) Math.random()));
    }
}
