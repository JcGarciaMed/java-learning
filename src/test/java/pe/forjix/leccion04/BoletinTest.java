package pe.forjix.leccion04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ADELANTO de la Lección 16 (Testing). No hace falta que entiendas todo aún.
 * Lo único que importa hoy: esto detecta la regresión de max() en 2 segundos.
 *
 * Ejecuta:  mvn test
 */
class BoletinTest {

    private static final int[] SCORES = {15, 8, 18, 11, 20, 7, 13};

    @Test
    void maxDevuelveLaNotaMasAlta() {
        assertEquals(20, Boletin.max(SCORES));
    }

    @Test
    void minDevuelveLaNotaMasBaja() {
        assertEquals(7, Boletin.min(SCORES));
    }

    /** Caso que delata el bug: min() acierta con SCORES por casualidad, aquí no. */
    @Test
    void minFuncionaAunqueLaMasBajaNoSeaLaUltima() {
        assertEquals(7, Boletin.min(new int[]{7, 8}));
    }

    @Test
    void averageCalculaConDecimales() {
        // El tercer argumento es la tolerancia: nunca compares double con ==.
        assertEquals(13.142857, Boletin.average(SCORES), 0.000001);
    }

    @Test
    void countPassingCuentaLosAprobados() {
        assertEquals(5, Boletin.countPassing(SCORES));
    }

    /** Verifica el 'case 0' que faltaba en la Lección 3. */
    @Test
    void categoryOfCubreLasNotasMasBajas() {
        assertEquals("deficient", Boletin.categoryOf(3));
    }
}
