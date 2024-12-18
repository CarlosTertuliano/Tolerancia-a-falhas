package com.ft.store.fault;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class ErrorFailure {
    private final Random random = new Random();

    public boolean shouldFail(double probability) {
        return random.nextDouble() < probability;
    }
}
