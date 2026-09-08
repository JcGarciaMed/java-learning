package pe.forjix.leccion07;

public final class InternationalShipment extends Shipment {

    private static final double CUSTOMS_FEE = 20.0;
    private static final double WEIGHT_FEE = 8.0;
    private static final double WEIGHT_LIMIT = 10.0;

    public InternationalShipment(String trackingCode, double weightKg) {
        super(trackingCode, weightKg);
    }

    @Override
    double cost() {
        var weightCost = 0.0;
        if (getWeightKg() > WEIGHT_LIMIT) {
            weightCost = (getWeightKg() - WEIGHT_LIMIT) * WEIGHT_FEE;
        }
        return getWeightKg() * PRIMARY_FEE + CUSTOMS_FEE + weightCost;
    }

    @Override
    String type() {
        return "International";
    }
}
