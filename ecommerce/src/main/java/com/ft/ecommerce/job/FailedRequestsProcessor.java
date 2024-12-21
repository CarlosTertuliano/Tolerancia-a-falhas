package com.ft.ecommerce.job;

import com.ft.ecommerce.domain.BonusRequest;
import com.ft.ecommerce.failed_requests_logger.FailedRequests;
import com.ft.ecommerce.service.EcommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FailedRequestsProcessor {

    @Autowired
    private FailedRequests failedRequestsStore;

    @Autowired
    private EcommerceService ecommerceService;

    @Scheduled(fixedRate = 10000) // Executa a cada 10 segundos
    public void processFailedRequests() {
        if(ecommerceService.isFt()) {
            for (BonusRequest request : failedRequestsStore.getAndClear()) {
                boolean success = ecommerceService.applyBonusToUser(request.getIdUsuario(), (double) request.getValue());
                if (!success) {
                    failedRequestsStore.add(request); // Re-adiciona se falhar novamente
                }
            }
        }
    }
}
