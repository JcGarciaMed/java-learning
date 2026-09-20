package pe.forjix.leccion08;

public interface Shipment {
    double PRIMARY_FEE = 5.0;
    double CUSTOMS_FEE = 20.0;
    double WEIGHT_FEE = 8.0;
    double WEIGHT_LIMIT = 10.0;
    double EXPRESS_FEE = 1.8;
    double FIXED_COST = 15.0;

    String trackingCode();

    double weightKg();

    double cost();

    String type();

    default String label() {
        return "%s tracking code costs %.2f".formatted(trackingCode(), cost());
    }
}
