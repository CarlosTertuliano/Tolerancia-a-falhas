package com.ft.ecommerce.failed_requests_logger;

import com.ft.ecommerce.domain.BonusRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FailedRequests {
    private final Set<BonusRequest> failedRequests = new HashSet<>();

    public synchronized void add(BonusRequest request) {
        failedRequests.add(request);
    }

    public synchronized List<BonusRequest> getAndClear() {
        List<BonusRequest> currentRequests = new ArrayList<>(failedRequests);
        failedRequests.clear();
        return currentRequests;
    }
}
