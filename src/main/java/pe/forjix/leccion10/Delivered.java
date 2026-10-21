package pe.forjix.leccion10;

public record Delivered(String signedBy) implements DeliveryOutcome {

    public Delivered {
        if (signedBy == null || signedBy.isBlank()) {
            throw new IllegalArgumentException("Una entrega necesita quien firme");
        }
        signedBy = signedBy.strip();
    }
}
