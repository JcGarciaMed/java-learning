package pe.forjix.leccion10;

public record Returned(String reason) implements DeliveryOutcome {

    public Returned {
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Una devolucion necesita un motivo");
        }
        reason = reason.strip();
    }
}
