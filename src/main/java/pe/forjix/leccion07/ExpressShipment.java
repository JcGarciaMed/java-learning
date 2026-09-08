package pe.forjix.leccion07;

public final class ExpressShipment extends Shipment {
    private static final double EXPRESS_FEE = 1.8;
    private final double FIXED_COST = 15.0;

    public ExpressShipment(String trackingCode, double weightKg) {
        super(trackingCode, weightKg);
    }

    @Override
    double cost() {
        return getWeightKg() * PRIMARY_FEE * EXPRESS_FEE + FIXED_COST;
    }

    @Override
    String type() {
        return "Express";
    }
}
