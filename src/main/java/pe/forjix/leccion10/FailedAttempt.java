package pe.forjix.leccion10;

public record FailedAttempt(String reason, int attemptNumber) implements DeliveryOutcome {

    public FailedAttempt {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Un intento fallido necesita un motivo");
        }
        if (attemptNumber < 1) {
            throw new IllegalArgumentException("El numero de intento empieza en 1, llego: " + attemptNumber);
        }
        reason = reason.strip();
    }
}
