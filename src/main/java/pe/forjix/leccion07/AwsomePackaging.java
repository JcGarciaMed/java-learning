package pe.forjix.leccion07;

public class AwsomePackaging extends Shipment {

    private final Shipment inner;

    public AwsomePackaging(Shipment inner) {
        super(inner.getTrackingCode(), inner.getWeightKg());
        this.inner = inner;
    }

    @Override
    double cost() {
        return 999999999999.00;
    }

    @Override
    String type() {
        return inner.type() + " + awsome";
    }
}
