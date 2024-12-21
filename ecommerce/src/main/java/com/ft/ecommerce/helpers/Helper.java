package com.ft.ecommerce.helpers;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class Helper {

    public static class CircuitBreaker {
        private static boolean open = false;

        // Método estático para que a classe possa ser usada sem instância
        public static synchronized <T> T run(Supplier<T> action) {
            if (open) {
                throw new IllegalStateException("Circuit breaker está aberto!");
            }

            try {
                return action.get();
            } catch (Exception e) {
                open = true; // Abrir o circuito após falha
                System.err.println("Circuit breaker ativado!");
                throw e;
            }
        }

        // Método estático para resetar o circuito
        public static synchronized void reset() {
            open = false; // Resetar o circuito
        }

        // Método estático para resetar o circuito após um tempo
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
