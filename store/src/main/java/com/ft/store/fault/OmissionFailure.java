package com.ft.store.fault;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OmissionFailure {
    private final Random random = new Random();

    public boolean shouldFail(double probability) {
        return random.nextDouble() < probability;
    }

    public void applyCrash() throws InterruptedException {
        Thread.sleep(10000);
    }
}
