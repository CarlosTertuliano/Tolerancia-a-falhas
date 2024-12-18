package com.ft.fidelity.fault;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TimeFailure {
    private final Random random = new Random();

    public boolean shouldFail(double probability) {
        return random.nextDouble() < probability;
    }

    public void applyFailure() throws InterruptedException {
        Thread.sleep(2000);
    }
}
