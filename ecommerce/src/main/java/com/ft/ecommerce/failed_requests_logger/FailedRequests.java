package com.ft.ecommerce.failed_requests_logger;

import com.ft.ecommerce.domain.BonusRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FailedRequests {
    private final List<BonusRequest> failedRequests = new ArrayList<>();

    public synchronized void add(BonusRequest request) {
        failedRequests.add(request);
    }

    public synchronized List<BonusRequest> getAndClear() {
        List<BonusRequest> currentRequests = new ArrayList<>(failedRequests);
        failedRequests.clear();
        return currentRequests;
    }
}
