package com.jorge.learning.module01_fundamentos;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorArchivo {

    public List<String> leerLineas(Path ruta) throws IOException {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no puede ser null");
        }
        if (!Files.exists(ruta)) {
            throw new IOException("Archivo no encontrado: " + ruta);
        }

        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(ruta)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        }
        return lineas;
    }

    public long contarLineasNoVacias(Path ruta) throws IOException {
        return leerLineas(ruta).stream()
                .filter(linea -> !linea.isBlank())
                .count();
    }
}
