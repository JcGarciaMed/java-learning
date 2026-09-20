package pe.forjix.leccion08;


import java.util.Objects;

final class InsuredShipment implements Shipment {
    private final Shipment inner;      // TIENE un envío, en lugar de SER uno concreto

    public InsuredShipment(Shipment inner) {
        this.inner = Objects.requireNonNull(inner, "El envío no puede ser null");
    }

    @Override
    public String trackingCode() {
        return inner.trackingCode();
    }

    @Override
    public double weightKg() {
        return inner.weightKg();
    }

    @Override
    public double cost() {
        return inner.cost() * 1.02;
    }

    @Override
    public String type() {
        return inner.type() + " + insured";
    }
}
