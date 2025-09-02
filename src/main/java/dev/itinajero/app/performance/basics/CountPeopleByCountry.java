package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CountPeopleByCountry {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Iniciando conteo de personas por país: " + filePath);
        long startTime = System.currentTimeMillis();
        Map<String, Integer> countryCount = new HashMap<>();
        int totalLines = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                String[] fields = line.split(",");
                if (fields.length >= 9) {
                    String country = fields[8].trim();
                    countryCount.put(country, countryCount.getOrDefault(country, 0) + 1);
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }

        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas procesadas: " + totalLines);
        log("Personas por país:");
        for (Map.Entry<String, Integer> entry : countryCount.entrySet()) {
            log(entry.getKey() + ": " + entry.getValue());
            //System.out.println(entry.getValue());
        }
        log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");

    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }
}
