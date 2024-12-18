package com.ft.exchange.fault;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CrashFailure {
    private final Random random = new Random();

    public boolean shouldFail(double probability) {
        return random.nextDouble() < probability;
    }

    public void applyCrash() {
        System.exit(1);
    }
}
