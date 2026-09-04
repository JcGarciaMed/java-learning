package pe.forjix.leccion06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void rechazaNotaFueraDeEscala() {
        assertThrows(IllegalArgumentException.class, () -> new Student("Jorge", 47));
    }

    @Test
    void rechazaNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> new Student("", 12));
    }

    @Test
    void validaIsPassing() {
        Student student = new Student("Jorge", 10);
        assertFalse(student.isPassing());
        student = new Student("Jorge", 13);
        assertTrue(student.isPassing());
    }
}