package pe.forjix.leccion07;

import java.util.Objects;


public abstract class Shipment {

    protected static final double PRIMARY_FEE = 5.0;
    private String trackingCode;
    private double weightKg;

    public Shipment(String trackingCode, double weightKg) {
        if (trackingCode.isEmpty() || Objects.isNull(weightKg)) {
            throw new IllegalArgumentException("Tracking code and weight must be provided");
        }
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        this.trackingCode = trackingCode;
        this.weightKg = weightKg;
    }

    abstract double cost();

    abstract String type();

    final String label() {
        return "%s tracking code costs %.2f".formatted(trackingCode, cost());
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public double getWeightKg() {
        return weightKg;
    }
}
