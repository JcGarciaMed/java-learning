package pe.forjix.leccion07;

public final class StandardShipment extends Shipment {

    public StandardShipment(String trackingCode, double weightKg) {
        super(trackingCode, weightKg);
    }

    @Override
    double cost() {
        return getWeightKg() * PRIMARY_FEE;
    }

    @Override
    String type() {
        return "Standard";
    }

}
