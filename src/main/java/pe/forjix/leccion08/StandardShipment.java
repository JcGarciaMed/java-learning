package pe.forjix.leccion08;


public final class StandardShipment extends AbstractShipment {

    public StandardShipment(String trackingCode, double weightKg) {
        super(trackingCode, weightKg);
    }

    @Override
    public double cost() {
        return weightKg() * PRIMARY_FEE;
    }

    @Override
    public String type() {
        return "Standard";
    }
}
