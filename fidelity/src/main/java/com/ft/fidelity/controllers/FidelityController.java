package com.ft.fidelity.controllers;

import com.ft.fidelity.domain.BonusRequest;
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

    @PostMapping("")
    public ResponseEntity<String> bonus(@RequestBody BonusRequest bonusRequest) {

        // verificar se vai falhar

        fidelityService.saveBonus(bonusRequest);
        return ResponseEntity.ok("Bônus cadastrado com sucesso!");
    }
}
