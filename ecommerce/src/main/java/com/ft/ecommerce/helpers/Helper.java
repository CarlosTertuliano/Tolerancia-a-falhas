package com.ft.ecommerce.helpers;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class Helper {

    public static class CircuitBreaker {
        private static boolean open = false;

        public static synchronized <T> T run(Supplier<T> action) {
            if (open) {
                throw new IllegalStateException("Circuit breaker está aberto!");
            }

            try {
                return action.get();
            } catch (Exception e) {
                open = true;
                System.err.println("Circuit breaker ativado!");
                throw e;
            }
        }

        public static synchronized void reset() {
            open = false;
            System.out.println("========================================= Circuit breaker fechou! =========================================");
        }

        public static synchronized void resetAfter(Duration duration) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    reset();
                }
            }, duration.toMillis());
        }
    }
}
