package pe.forjix.leccion10;

/**
 * Operaciones sobre la jerarquia sellada DeliveryOutcome.
 * Ningun switch tiene default: si aparece una variante nueva, el compilador
 * senala cada metodo que hay que actualizar.
 */
public final class DeliveryReports {

    private static final double REPEATED_FAILURE_FEE = 10.00;
    private static final double RETURN_FEE = 15.00;
    private static final int ATTEMPTS_BEFORE_FEE = 3;

    private DeliveryReports() {
        // clase de utilidades: no se instancia
    }

    /**
     * Record patterns: se desestructura cada variante directamente en el case.
     */
    public static String describe(DeliveryOutcome outcome) {
        return switch (outcome) {
            case Delivered(String signedBy) -> "Entregado, firmo " + signedBy;
            case FailedAttempt(String reason, int n) -> "Intento " + n + " fallido: " + reason;
            case Returned(String reason) -> "Devuelto: " + reason;
            case Lost(String reportedBy) -> "Perdido, reportado por " + reportedBy;
        };
    }

    /**
     * Guard 'when': el caso especifico va ANTES que el general.
     */
    public static double surcharge(DeliveryOutcome outcome) {
        return switch (outcome) {
            case Delivered d -> 0.00;
            case FailedAttempt f when f.attemptNumber() >= ATTEMPTS_BEFORE_FEE -> REPEATED_FAILURE_FEE;
            case FailedAttempt f -> 0.00;
            case Returned r -> RETURN_FEE;
            case Lost l -> 0.00;
        };
    }

    /**
     * En Java 21 no se pueden juntar dos patrones en un mismo case
     * ("case FailedAttempt f, Returned r" no compila). Desde Java 22, con las
     * variables anonimas, se podra escribir: case FailedAttempt _, Returned _ -> true
     */
    public static boolean needsCustomerContact(DeliveryOutcome outcome) {
        return switch (outcome) {
            case Delivered d -> false;
            case FailedAttempt f -> true;
            case Returned r -> true;
            case Lost l -> true;
        };
    }

    public static void main(String[] args) {
        final DeliveryOutcome[] outcomes = {
                new Delivered("  Ana Torres "),
                new FailedAttempt("cliente ausente", 1),
                new FailedAttempt("direccion cerrada", 3),
                new Returned("direccion incorrecta"),
                new Lost("centro de distribucion Callao")
        };

        System.out.printf("%-52s %10s %10s%n", "RESULTADO", "RECARGO", "CONTACTAR");
        System.out.println("-".repeat(74));
        for (final DeliveryOutcome o : outcomes) {
            System.out.printf("%-52s %10.2f %10s%n",
                    describe(o), surcharge(o), needsCustomerContact(o) ? "si" : "no");
        }
    }
}
