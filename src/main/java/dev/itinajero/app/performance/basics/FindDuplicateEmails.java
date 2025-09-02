package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FindDuplicateEmails {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Iniciando búsqueda de emails duplicados: " + filePath);
        long startTime = System.currentTimeMillis();
        Set<String> seenEmails = new HashSet<>();
        Set<String> duplicateEmails = new HashSet<>();
        int totalLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                String[] fields = line.split(",");
                if (fields.length >= 3) {
                    String email = fields[2].trim();
                    if (!seenEmails.add(email)) {
                        duplicateEmails.add(email);
                    }
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas procesadas: " + totalLines);
        log("Total de emails duplicados: " + duplicateEmails.size());
        if (!duplicateEmails.isEmpty()) {
            log("Ejemplo de emails duplicados:");
            int count = 0;
            for (String email : duplicateEmails) {
                log(email);
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
