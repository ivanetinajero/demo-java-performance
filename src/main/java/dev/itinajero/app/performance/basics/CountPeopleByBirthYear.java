package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cuenta cuántas personas hay por año de nacimiento en un archivo de texto.
 * Usa LocalDate y DateTimeFormatter para practicar fechas modernas en Java.
 *
 * Ejemplo de línea:
 *   Juan,Pérez,juan.perez@email.com,1987-05-12,México,15000
 *   Ana,López,ana.lopez@email.com,1995-11-23,España,18000
 *   Pedro,Gómez,pedro.gomez@email.com,1987-03-01,Chile,12000
 *
 * Resultado esperado:
 *   1987: 2
 *   1995: 1
 */
public class CountPeopleByBirthYear {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Iniciando conteo de personas por año de nacimiento: " + filePath);
        long startTime = System.currentTimeMillis();
        Map<Integer, Integer> yearCount = new HashMap<>();
        int totalLines = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                String[] fields = line.split(",");
                if (fields.length >= 4) {
                    String birthDateStr = fields[3].trim();
                    try {
                        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
                        int year = birthDate.getYear();
                        yearCount.put(year, yearCount.getOrDefault(year, 0) + 1);
                    } catch (Exception e) {
                        // Fecha inválida, ignorar registro
                    }
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas procesadas: " + totalLines);
        log("Personas por año de nacimiento:");
        yearCount.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> log(entry.getKey() + ": " + entry.getValue()));
        log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");
    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
