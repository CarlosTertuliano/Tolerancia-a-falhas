package com.ft.fidelity.controllers;

import com.ft.fidelity.domain.BonusRequest;
import com.ft.fidelity.fault.TimeFailure;
import com.ft.fidelity.services.FidelityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bonus")
public class FidelityController {

    @Autowired
    private FidelityService fidelityService;
    @Autowired
    private TimeFailure timeFailure;

    @PostMapping("")
    public ResponseEntity<String> bonus(@RequestBody BonusRequest bonusRequest) throws InterruptedException {

        if(timeFailure.shouldFail(0.1)){
            timeFailure.applyFailure();
        }

        fidelityService.saveBonus(bonusRequest);
        return ResponseEntity.ok("Bônus cadastrado com sucesso!");
    }
}
