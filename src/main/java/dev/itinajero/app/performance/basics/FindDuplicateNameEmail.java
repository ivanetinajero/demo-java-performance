package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Este programa busca registros duplicados en un archivo de texto,
 * considerando como clave de agrupamiento la combinación de nombre, apellidos y email.
 * Es similar a hacer un GROUP BY nombre, apellidos, email en SQL y luego buscar los que aparecen más de una vez.
 *
 * Ejemplo de línea en el archivo:
 *   Juan,Pérez,juan.perez@email.com,35,México,15000
 *   Ana,López,ana.lopez@email.com,28,España,18000
 *   Juan,Pérez,juan.perez@email.com,35,México,15000
 *
 * En este caso, la combinación "Juan:Pérez:juan.perez@email.com" aparece dos veces, por lo que se detecta como duplicado.
 */
public class FindDuplicateNameEmail {
    public static void main(String[] args) {

        // Ruta del archivo a analizar. Cambia el nombre según el archivo que quieras procesar.
        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Iniciando búsqueda de duplicados por nombre, apellidos y email: " + filePath);
        long startTime = System.currentTimeMillis();
        // Este set almacena las combinaciones únicas de nombre:apellidos:email que ya se han visto
        Set<String> seenNameSurnameEmail = new HashSet<>();
        // Este set almacena las combinaciones que ya se detectaron como duplicadas
        Set<String> duplicateNameSurnameEmail = new HashSet<>();
        int totalLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                String[] fields = line.split(",");
                // Se espera que el archivo tenga al menos 3 campos: nombre, apellidos, email
                if (fields.length >= 3) {
                    String name = fields[0].trim();      // Nombre
                    String surname = fields[1].trim();   // Apellidos
                    String email = fields[2].trim();     // Email
                    // Se construye la clave de agrupamiento
                    String key = name + ":" + surname + ":" + email;
                    // Si la clave ya existía en el set, es un duplicado
                    if (!seenNameSurnameEmail.add(key)) {
                        duplicateNameSurnameEmail.add(key);
                    }
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas procesadas: " + totalLines);
        log("Total de registros duplicados (nombre + apellidos + email): " + duplicateNameSurnameEmail.size());
        if (!duplicateNameSurnameEmail.isEmpty()) {
            log("Ejemplo de duplicados (nombre + apellidos + email):");
            int count = 0;
            for (String key : duplicateNameSurnameEmail) {
                log(key); // Imprime la clave duplicada
                if (++count >= 10) break; // Muestra solo los primeros 10
            }
        }
        log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");

    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
