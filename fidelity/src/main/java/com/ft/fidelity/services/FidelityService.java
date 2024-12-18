package com.ft.fidelity.services;


import com.ft.fidelity.domain.BonusRequest;
import com.ft.fidelity.domain.UserBonus;
import com.ft.fidelity.repository.FidelityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FidelityService {

    @Autowired
    private FidelityRepository fidelityRepository;

    public void saveBonus(BonusRequest bonusRequest) {
        UserBonus userBonus = new UserBonus(bonusRequest.getIdUsuario(), bonusRequest.getValue());

        System.out.println("Bonus cadastrado: " + userBonus.getBonus() + " para: " + userBonus.getUserId());
        fidelityRepository.save(userBonus);
    }
}
