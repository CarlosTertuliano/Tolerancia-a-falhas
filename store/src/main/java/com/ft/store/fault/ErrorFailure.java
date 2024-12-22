package com.ft.store.fault;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class ErrorFailure {
    private final Random random = new Random();

    private Instant failureStartTime = null;
    private static final long FAILURE_DURATION = 5_000;


    public boolean shouldFail(double probability) {
        if (failureStartTime != null && Instant.now().toEpochMilli() - failureStartTime.toEpochMilli() < FAILURE_DURATION) {
            return true;
        }
        failureStartTime = null;
        return random.nextDouble() < probability;
    }

    public void applyFailure() {
        if(failureStartTime == null){
            failureStartTime = Instant.now();
        }
    }
}
