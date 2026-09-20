package pe.forjix.leccion08;


import java.util.Objects;

public class AwsomePackaging implements Shipment {

    private final Shipment inner;

    public AwsomePackaging(Shipment inner) {
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
        return 999999999999.00;
    }

    @Override
    public String type() {
        return inner.type() + " + awesome!!!!";
    }
}
