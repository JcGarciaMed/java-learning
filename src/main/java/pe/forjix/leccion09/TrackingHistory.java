package pe.forjix.leccion09;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public class TrackingHistory {

    private final List<TrackingEvent> events;

    private final EnumMap<ShipmentStatus, Integer> statusCounts;

    private final String shipmentCode;

    public TrackingHistory(String shipmentCode) {
        this.shipmentCode = Objects.requireNonNull(shipmentCode, "El código es obligatorio");
        this.events = new ArrayList<>();
        this.statusCounts = new EnumMap<>(ShipmentStatus.class);
    }

    public void addEvent(TrackingEvent event) {
        var currentStatus = currentStatus();
        if (Objects.isNull(currentStatus) || currentStatus.canTransitionTo(event.status())) {
            events.add(event);
            statusCounts.put(event.status(), statusCounts.getOrDefault(event.status(), 0) + 1);
        } else {
            throw new IllegalArgumentException("Invalid transition from " + currentStatus + " to " + event.status());
        }
    }

    public ShipmentStatus currentStatus() {
        return events.isEmpty() ? null : events.getLast().status();
    }

    public boolean isDelivered() {
        return currentStatus() == ShipmentStatus.DELIVERED;
    }

}
