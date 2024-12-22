package com.ft.store.controllers;

import com.ft.store.fault.ErrorFailure;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sell")
public class StoreController {

    @Autowired
    private ErrorFailure errorFailure;

    private Boolean allwaysFail = false;

    @PostMapping("")
    public ResponseEntity<Integer> sellProduct(@RequestParam Integer id) {

        if(errorFailure.shouldFail(0.1) || allwaysFail) {
            System.out.println("\nStore failed! - SERVER FAILURE - internal server error \n");

            errorFailure.applyFailure();
            return ResponseEntity.internalServerError().build();
        }

        System.out.println("\nVenda realizada com sucesso!\n");
        return ResponseEntity.ok().body(Math.round((float) Math.random()));
    }

    @GetMapping("")
    public Boolean setAllwaysFail(@RequestParam Boolean allwaysFail) {
        this.allwaysFail = allwaysFail;
        return allwaysFail;
    }
}
