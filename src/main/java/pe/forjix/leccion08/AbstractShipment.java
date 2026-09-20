package pe.forjix.leccion08;

import java.util.Objects;

abstract class AbstractShipment implements Shipment {
    private final String trackingCode;
    private final double weightKg;

    // constructor con la validación, y los dos accesores
    public AbstractShipment(String trackingCode, double weightKg) {
        if (trackingCode.isEmpty() || Objects.isNull(weightKg)) {
            throw new IllegalArgumentException("Tracking code and weight must be provided");
        }
        if (weightKg <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        this.trackingCode = trackingCode;
        this.weightKg = weightKg;
    }

    @Override
    public String trackingCode() {
        return trackingCode;
    }

    @Override
    public double weightKg() {
        return weightKg;
    }
}
