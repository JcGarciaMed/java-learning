package pe.forjix.leccion07;

public class ShippingBatch {
    private final Shipment[] shipments;
    private final String header = String.format("%-15s %6s %10s %6s", "CODIGO", "TIPO", "PESO", "COSTO");

    public ShippingBatch(Shipment[] shipments) {
        this.shipments = shipments.clone();
    }

    public double totalCost() {
        var totalCost = 0.0;
        for (final Shipment shipment : shipments) {
            totalCost += shipment.cost();
        }
        return totalCost;
    }

    public String report() {
        StringBuilder report = new StringBuilder();
        final String separator = "-".repeat(header.length());
        report.append(header).append('\n');
        for (final Shipment shipment : shipments) {
            /*report.append(String.format("%-15s %6d %.2f %.2f", shipment.getTrackingCode(),
                            shipment.type(),
                            shipment.getWeightKg(),
                            shipment.cost()))
                    .append('\n');*/
            report.append(String.format("%-15s %12s %10.2f %10.2f%n",
                    shipment.getTrackingCode(),
                    shipment.type(),
                    shipment.getWeightKg(),
                    shipment.cost()));
        }
        report.append(separator).append('\n');
        report.append(String.format("TOTAL COST: %.2f", totalCost())).append('\n');
        return report.toString();
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ShippingBatch shippingBatch = new ShippingBatch(new Shipment[]{new ExpressShipment("XP-44522", 85.00),
                new StandardShipment("SP-5222", 152.2),
                new InternationalShipment("IP-5222", 152.3),
                new InsuredShipment(new StandardShipment("SP-5222", 666.0)),
                new AwsomePackaging(new InsuredShipment(new InternationalShipment("TOO-NID", 145.85)))});
        System.out.println(shippingBatch.report());
    }
}
