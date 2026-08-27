// 1) PAQUETE: la "carpeta lógica" de la clase. DEBE coincidir con la ruta física
//    del archivo: pe/forjix/leccion01/Hola.java
package pe.forjix.leccion01;

// 2) CLASE: en Java TODO vive dentro de una clase. No existe el código suelto.
//    El nombre de la clase (Hola) debe ser idéntico al del archivo (Hola.java).
//    'public' = visible desde cualquier otro paquete.
public class Hola {

    // 3) MÉTODO main: el punto de entrada. La JVM busca exactamente esta firma.
    //    public  -> accesible desde fuera
    //    static  -> pertenece a la CLASE, no a un objeto (no hace falta crear un Hola)
    //    void    -> no devuelve nada
    //    String[] args -> los argumentos de la línea de comandos
    public static void main(String[] args) {

        // 4) SENTENCIA: toda instrucción termina en punto y coma.
        //    System.out es la salida estándar; println imprime y salta de línea.
        System.out.println("Hola, Java 21.");

        // Java es de TIPADO ESTÁTICO: cada variable declara su tipo y el
        // compilador lo verifica ANTES de ejecutar.
        String lenguaje = "Java";
        int version = 21;

        // Concatenación con '+'
        System.out.println("Estoy aprendiendo " + lenguaje + " " + version + ".");
    }
}
