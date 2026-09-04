package pe.forjix.leccion06;

/**
 * Referencia de la Lección 6: campos, constructor, this, encapsulamiento,
 * static vs instancia, e inmutabilidad.
 */
public class Cuenta {

    // ---- static: pertenece a la CLASE, es uno solo para todas las cuentas ----
    private static int cuentasCreadas = 0;
    private static final double COMISION = 0.005;

    // ---- campos de instancia: cada objeto tiene los SUYOS ----
    private final String titular;   // final: se fija en el constructor y no cambia jamas
    private final String numero;
    private double saldo;           // sin final: este si evoluciona

    /**
     * Constructor: mismo nombre que la clase, SIN tipo de retorno.
     */
    public Cuenta(String titular, double saldoInicial) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("El titular es obligatorio");
        }
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.titular = titular.strip();     // 'this.titular' = el campo; 'titular' = el parametro
        this.saldo = saldoInicial;
        cuentasCreadas++;                   // sin 'this': es de la clase, no del objeto
        this.numero = String.format("AC-%04d", cuentasCreadas);
    }

    /**
     * Sobrecarga de constructor: delega en el principal con 'this(...)'.
     */
    public Cuenta(String titular) {
        this(titular, 0);                   // debe ser la PRIMERA sentencia
    }

    // ---- Metodos de instancia: operan sobre SU propio estado ----

    public void depositar(double monto) {
        exigirMontoPositivo(monto);
        saldo += monto;                     // 'this' es opcional aqui
    }

    public void retirar(double monto) {
        exigirMontoPositivo(monto);
        final double total = monto + monto * COMISION;
        if (total > saldo) {
            throw new IllegalStateException(
                    "Fondos insuficientes: necesitas %.2f y tienes %.2f".formatted(total, saldo));
        }
        saldo -= total;
    }

    /**
     * Metodo privado de apoyo: detalle interno, nadie de fuera lo ve.
     */
    private void exigirMontoPositivo(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo, llego: " + monto);
        }
    }

    // Getters solo para lo que de verdad hace falta leer. NO hay setSaldo():
    // el saldo solo cambia por depositar() o retirar(), que aplican las reglas.
    public String titular() {
        return titular;
    }

    public String numero() {
        return numero;
    }

    public double saldo() {
        return saldo;
    }

    /**
     * Metodo static: es de la clase, no de ninguna cuenta concreta.
     */
    public static int cuentasCreadas() {
        return cuentasCreadas;
    }

    /**
     * toString se llama solo al imprimir el objeto o concatenarlo.
     */
    @Override
    public String toString() {
        return "%s [%s] saldo=%.2f".formatted(titular, numero, saldo);
    }

    // ------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("--- Crear objetos ---");
        final Cuenta a = new Cuenta("Jorge Garcia", 1000);
        final Cuenta b = new Cuenta("Maria Lopez");     // usa la sobrecarga
        System.out.println(a);                          // llama a toString() implicitamente
        System.out.println(b);

        System.out.println("\n--- Cada objeto tiene su propio estado ---");
        a.depositar(500);
        b.depositar(50);
        System.out.println(a);
        System.out.println(b);

        System.out.println("\n--- El estado static es COMPARTIDO ---");
        System.out.println("cuentas creadas: " + Cuenta.cuentasCreadas());

        System.out.println("\n--- El objeto se defiende de estados invalidos ---");
        intentar(() -> new Cuenta("", 100));
        intentar(() -> a.depositar(-50));
        intentar(() -> b.retirar(999_999));

        System.out.println("\n--- Referencias: dos variables, UN objeto ---");
        final Cuenta alias = a;         // NO copia el objeto, copia la referencia
        alias.depositar(1);
        System.out.println("a     : " + a);
        System.out.println("alias : " + alias);
        System.out.println("a == alias : " + (a == alias));   // true: son el mismo objeto
    }

    /**
     * Ejecuta algo y muestra el error en vez de reventar. Lo entenderas en la Leccion 13.
     */
    private static void intentar(Runnable accion) {
        try {
            accion.run();
        } catch (RuntimeException e) {
            System.out.println("  bloqueado -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
