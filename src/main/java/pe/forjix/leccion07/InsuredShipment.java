package pe.forjix.leccion07;

final class InsuredShipment extends Shipment {
    private final Shipment inner;      // TIENE un envío, en lugar de SER uno concreto
    private static final double INSURANCE_RATE = 0.02;

    public InsuredShipment(Shipment inner) {
        super(inner.getTrackingCode(), inner.getWeightKg());
        this.inner = inner;
    }

    @Override
    public double cost() {
        return inner.cost() * (1 + INSURANCE_RATE);
    }

    @Override
    String type() {
        return inner.type() + " + insured";
    }
}
