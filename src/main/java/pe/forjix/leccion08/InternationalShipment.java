package pe.forjix.leccion08;


public final class InternationalShipment extends AbstractShipment {

    public InternationalShipment(String trackingCode, double weightKg) {
        super(trackingCode, weightKg);
    }

    @Override
    public double cost() {
        var weightCost = 0.0;
        if (weightKg() > WEIGHT_LIMIT) {
            weightCost = (weightKg() - WEIGHT_LIMIT) * WEIGHT_FEE;
        }
        return weightKg() * PRIMARY_FEE + CUSTOMS_FEE + weightCost;
    }

    @Override
    public String type() {
        return "International";
    }
}
