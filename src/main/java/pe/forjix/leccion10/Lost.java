package pe.forjix.leccion10;

public record Lost(String reportedBy) implements DeliveryOutcome {

    public Lost {
        if (reportedBy == null || reportedBy.isBlank()) {
            throw new IllegalArgumentException("Una perdida necesita quien la reporte");
        }
        reportedBy = reportedBy.strip();
    }
}
