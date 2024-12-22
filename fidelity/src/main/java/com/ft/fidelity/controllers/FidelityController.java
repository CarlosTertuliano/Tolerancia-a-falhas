package com.ft.fidelity.controllers;

import com.ft.fidelity.domain.BonusRequest;
import com.ft.fidelity.fault.TimeFailure;
import com.ft.fidelity.services.FidelityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bonus")
public class FidelityController {

    @Autowired
    private FidelityService fidelityService;
    @Autowired
    private TimeFailure timeFailure;

    private Boolean allwaysFail = false;

    @PostMapping("")
    public ResponseEntity<String> bonus(@RequestBody BonusRequest bonusRequest) throws InterruptedException {

        if(timeFailure.shouldFail(0.1) || allwaysFail) {
            System.out.println("\nFidelity failed! - TIME FAILURE - sleep de 2s \n");
            timeFailure.applyFailure();
            return ResponseEntity.internalServerError().body("");
        }

        fidelityService.saveBonus(bonusRequest);
        return ResponseEntity.ok("\nBônus cadastrado com sucesso!\n");
    }

    @GetMapping("")
    public Boolean setAllwaysFail(@RequestParam Boolean allwaysFail) {
        this.allwaysFail = allwaysFail;
        return allwaysFail;
    }
}
