package com.ft.fidelity.fault;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
public class TimeFailure {

    private Instant failureStartTime = null;
    private static final long FAILURE_DURATION = 30_000;


    private final Random random = new Random();

    public boolean shouldFail(double probability) {
        if (failureStartTime != null && Instant.now().toEpochMilli() - failureStartTime.toEpochMilli() < FAILURE_DURATION) {
            return true;
        }
        failureStartTime = null;
        return random.nextDouble() < probability;
    }

    public void applyFailure() throws InterruptedException {
        if(failureStartTime == null){
            failureStartTime = Instant.now();
        }
        Thread.sleep(2000);
    }
}
