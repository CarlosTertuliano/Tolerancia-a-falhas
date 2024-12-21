package com.ft.ecommerce.helpers;

public class RetryHelper {

    public static <T> T executeWithRetry(RetryableOperation<T> operation, int maxAttempts, long delay) {
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                return operation.execute();
            } catch (Exception e) {
                attempt++;

                if (attempt >= maxAttempts) {
                    throw new RuntimeException("Operation failed after " + maxAttempts + " attempts", e);
                }

                try {
                    Thread.sleep(delay); // Aguarda antes de tentar novamente
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Operação de Retry interrompida", ie);
                }
            }
        }
        throw new RuntimeException("Erro inesperado no mecanismo de retry");
    }

    @FunctionalInterface
    public interface RetryableOperation<T> {
        T execute() throws Exception;
    }
}