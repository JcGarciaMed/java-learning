package pe.forjix.leccion09;

import java.util.Objects;

public record TrackingEvent(String shipmentCode, ShipmentStatus status, String location) {
    public TrackingEvent {
        Objects.requireNonNull(shipmentCode);
        Objects.requireNonNull(status);
        Objects.requireNonNull(location);
        if (shipmentCode.isBlank() || location.isBlank()) {
            throw new IllegalArgumentException("Invalid tracking event");
        }
    }
}
