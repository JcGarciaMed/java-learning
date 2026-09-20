package pe.forjix.leccion08;


public final class ExpressShipment extends AbstractShipment {

    public ExpressShipment(String trackingCode, double weightKg) {
        super(trackingCode, weightKg);
    }

    @Override
    public double cost() {
        return weightKg() * PRIMARY_FEE * EXPRESS_FEE + FIXED_COST;
    }

    @Override
    public String type() {
        return "express";
    }
}
