package com.jorge.learning.module01_fundamentos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LectorArchivoTest {

    private LectorArchivo lectorArchivo;

    /** Directorio temporal único por test — JUnit lo crea y borra automáticamente. */
    @TempDir
    Path tempDir;

    private Path archivoDatos;

    @BeforeEach
    void setUp() throws IOException {
        lectorArchivo = new LectorArchivo();

        // tempDir es una carpeta vacía y aislada; resolve() arma la ruta al archivo
        archivoDatos = tempDir.resolve("datos.txt");
        Files.writeString(archivoDatos, "Línea 1 de prueba\n\nLínea 2 de contenido");
    }

    @Test
    void leerLineasConTryWithResources() throws IOException {
        assertThat(lectorArchivo.leerLineas(archivoDatos)).hasSize(3);
        assertThat(lectorArchivo.leerLineas(archivoDatos).getFirst()).isEqualTo("Línea 1 de prueba");
    }

    @Test
    void contarLineasNoVacias() throws IOException {
        assertThat(lectorArchivo.contarLineasNoVacias(archivoDatos)).isEqualTo(2);
    }

    @Test
    void leerLineasRechazaRutaNull() {
        assertThatThrownBy(() -> lectorArchivo.leerLineas(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void archivoNoEncontradoLanzaIOException() {
        Path inexistente = tempDir.resolve("no-existe.txt");

        assertThatThrownBy(() -> lectorArchivo.leerLineas(inexistente))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("no encontrado");
    }
}
