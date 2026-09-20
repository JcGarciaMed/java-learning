package pe.forjix.leccion08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests de la Leccion 8.
 *
 * Nota sobre los decimales: NUNCA compares double con assertEquals de dos
 * argumentos. El tercer parametro es la TOLERANCIA (delta). 0.001 significa
 * "me vale cualquier valor que difiera en menos de una milesima".
 */
class ShipmentTest {

    /** Tolerancia para comparar dinero. */
    private static final double DELTA = 0.001;

    // =================================================================
    @Nested
    @DisplayName("Costos de cada tipo")
    class Costos {

        @Test
        @DisplayName("Standard: peso x 5.00")
        void standard() {
            assertEquals(20.00, new StandardShipment("ST-1", 4.0).cost(), DELTA);
        }

        @Test
        @DisplayName("Express: peso x 5.00 x 1.8 + 15.00 fijo")
        void express() {
            // 2 x 5 x 1.8 = 18.00, mas 15.00 de gestion = 33.00
            assertEquals(33.00, new ExpressShipment("EX-1", 2.0).cost(), DELTA);
        }

        @Test
        @DisplayName("International por debajo del limite: sin recargo por exceso")
        void internacionalLigero() {
            // 5 x 5 = 25.00, mas 20.00 de aduana, mas 0 de exceso = 45.00
            assertEquals(45.00, new InternationalShipment("IN-1", 5.0).cost(), DELTA);
        }
    }

    // =================================================================
    @Nested
    @DisplayName("Fronteras: el limite de 10 kg")
    class Fronteras {

        @Test
        @DisplayName("Exactamente 10 kg NO paga recargo")
        void justoEnElLimite() {
            // 10 x 5 = 50.00, mas 20.00 aduana, mas 0 de exceso = 70.00
            // Este es el caso frontera: el que distingue mayor-que de mayor-o-igual.
            assertEquals(70.00, new InternationalShipment("IN-2", 10.0).cost(), DELTA);
        }

        @Test
        @DisplayName("10.1 kg paga recargo solo por el exceso")
        void justoPorEncima() {
            // 10.1 x 5 = 50.50, mas 20.00 aduana, mas 0.1 x 8 = 0.80  ->  71.30
            assertEquals(71.30, new InternationalShipment("IN-3", 10.1).cost(), DELTA);
        }

        @Test
        @DisplayName("El recargo NUNCA puede ser negativo")
        void sinRecargoNegativo() {
            // Bug clasico: (peso - 10) * 8 sin Math.max convierte el recargo en descuento.
            final double internacional = new InternationalShipment("IN-4", 2.0).cost();
            final double estandar = new StandardShipment("ST-2", 2.0).cost();
            assertTrue(internacional > estandar,
                    "Un envio internacional nunca puede costar menos que uno estandar del mismo peso");
        }
    }

    // =================================================================
    @Nested
    @DisplayName("Validacion del constructor")
    class Validacion {

        @Test
        void rechazaPesoCero() {
            assertThrows(IllegalArgumentException.class,
                    () -> new StandardShipment("ST-3", 0));
        }

        @Test
        void rechazaPesoNegativo() {
            assertThrows(IllegalArgumentException.class,
                    () -> new StandardShipment("ST-4", -5));
        }

        @Test
        @DisplayName("Un codigo en blanco NO es un codigo valido")
        void rechazaCodigoEnBlanco() {
            assertThrows(IllegalArgumentException.class,
                    () -> new StandardShipment("   ", 4.0));
        }

        @Test
        @DisplayName("Un codigo null debe dar IllegalArgumentException, no NPE")
        void rechazaCodigoNull() {
            // Quien llama espera un error de ARGUMENTO con un mensaje util,
            // no un NullPointerException sin explicacion.
            assertThrows(IllegalArgumentException.class,
                    () -> new StandardShipment(null, 4.0));
        }
    }

    // =================================================================
    @Nested
    @DisplayName("Decoradores")
    class Decoradores {

        @Test
        @DisplayName("El seguro aplica 2% sobre un Standard")
        void seguroSobreStandard() {
            final Shipment base = new StandardShipment("ST-5", 4.0);        // 20.00
            assertEquals(20.40, new InsuredShipment(base).cost(), DELTA);
        }

        @Test
        @DisplayName("El mismo decorador funciona sobre un Express")
        void seguroSobreExpress() {
            final Shipment base = new ExpressShipment("EX-2", 2.0);         // 33.00
            assertEquals(33.66, new InsuredShipment(base).cost(), DELTA);
        }

        @Test
        @DisplayName("Los decoradores se anidan y componen el type()")
        void anidamientoDeTipos() {
            final Shipment envuelto =
                    new InsuredShipment(new AwsomePackaging(new StandardShipment("ST-6", 4.0)));
            assertEquals("Standard + awesome!!!! + insured", envuelto.type());
        }

        @Test
        @DisplayName("Un decorador DEBE delegar el costo en el envuelto")
        void decoradorDelega() {
            // No asumo que tarifa aplica. Solo exijo el contrato del patron:
            // si el envuelto cuesta mas, el decorado tambien debe costar mas.
            final Shipment ligero = new AwsomePackaging(new StandardShipment("ST-7", 1.0));
            final Shipment pesado = new AwsomePackaging(new StandardShipment("ST-8", 100.0));
            assertTrue(pesado.cost() > ligero.cost(),
                    "Un decorador que ignora inner.cost() no decora: reemplaza");
        }

        @Test
        @DisplayName("El decorador delega los datos del envuelto")
        void delegaDatos() {
            final Shipment base = new StandardShipment("ST-9", 7.5);
            final Shipment deco = new InsuredShipment(base);
            assertEquals("ST-9", deco.trackingCode());
            assertEquals(7.5, deco.weightKg(), DELTA);
        }
    }

    // =================================================================
    @Nested
    @DisplayName("ShippingBatch y la estrategia de descuento")
    class Lote {

        private Shipment[] dosEnvios() {
            return new Shipment[]{
                    new StandardShipment("ST-A", 10.0),   // 50.00
                    new StandardShipment("ST-B", 20.0)    // 100.00
            };                                            // total bruto: 150.00
        }

        @Test
        @DisplayName("Sin descuento: el total es la suma")
        void sinDescuento() {
            final ShippingBatch lote = new ShippingBatch(dosEnvios(), cost -> cost);
            assertEquals(150.00, lote.totalCost(), DELTA);
        }

        @Test
        @DisplayName("El lote APLICA el descuento que recibe")
        void aplicaDescuento() {
            final ShippingBatch lote = new ShippingBatch(dosEnvios(), cost -> cost * 0.5);
            assertEquals(75.00, lote.totalCost(), DELTA);
        }

        @Test
        @DisplayName("Una regla condicional tambien se respeta")
        void descuentoCondicional() {
            final Discount envioGratisSobre500 = cost -> cost > 500 ? cost - 50 : cost;
            final ShippingBatch lote = new ShippingBatch(dosEnvios(), envioGratisSobre500);
            assertEquals(150.00, lote.totalCost(), DELTA);   // 150 no supera 500
        }

        @Test
        @DisplayName("Copia defensiva: modificar el array de origen no rompe el lote")
        void copiaDefensiva() {
            final Shipment[] origen = dosEnvios();
            final ShippingBatch lote = new ShippingBatch(origen, cost -> cost);
            origen[0] = null;                                 // sabotaje desde fuera
            assertEquals(150.00, lote.totalCost(), DELTA);
        }
    }
}
