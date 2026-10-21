package pe.forjix.leccion09;

import java.util.EnumSet;
import java.util.Set;

public enum ShipmentStatus {
    CREATED("Created", false),
    PICKED_UP("Picked up", false),
    IN_TRANSIT("In transit", false),
    OUT_FOR_DELIVERY("Out for delivery", false),
    DELIVERED("Delivery", true),
    RETURNED("Returned", true);

    private final String description;
    private final boolean finalState;

    ShipmentStatus(String description, boolean finalState) {
        this.description = description;
        this.finalState = finalState;
    }

    private Set<ShipmentStatus> allowedNextStates() {
        return switch (this) {                            // sin 'default': el compilador
            case CREATED -> EnumSet.of(PICKED_UP, RETURNED);
            case PICKED_UP -> EnumSet.of(IN_TRANSIT, RETURNED);
            case IN_TRANSIT -> EnumSet.of(OUT_FOR_DELIVERY, RETURNED);
            case OUT_FOR_DELIVERY -> EnumSet.of(DELIVERED, RETURNED);
            case DELIVERED, RETURNED -> EnumSet.noneOf(ShipmentStatus.class);
        };
    }

    public boolean isFinal() {
        return finalState;
    }

    public boolean canTransitionTo(ShipmentStatus next) {
        for (ShipmentStatus allowed : allowedNextStates()) {
            if (allowed == next) {
                return true;
            }
        }
        return false;
    }


}
