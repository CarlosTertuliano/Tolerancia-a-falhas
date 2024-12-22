package com.ft.ecommerce.job;

import com.ft.ecommerce.domain.BonusRequest;
import com.ft.ecommerce.failed_requests_logger.FailedRequests;
import com.ft.ecommerce.service.EcommerceService;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FailedRequestsProcessor {

    @Autowired
    private FailedRequests failedRequestsStore;

    @Autowired
    private EcommerceService ecommerceService;

    @Scheduled(fixedRate = 10000)
    public void processFailedRequests() {
        if(ecommerceService.isFt()) {
            List<BonusRequest> requestsToProcess = failedRequestsStore.getAndClear();

            for (BonusRequest request : requestsToProcess) {
                System.out.println("Processamento da lista de bônus\n");
                try {
                    boolean success = ecommerceService.applyBonusToUser(request.getIdUsuario(), (double) request.getValue());
                } catch (ReadTimeoutException e) {
                    System.out.println("\nProcessamento do item falhou na captura da falha!\n");
                    failedRequestsStore.add(request);
                }
            }
        }
    }
}
