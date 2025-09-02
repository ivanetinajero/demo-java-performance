package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupByAgeRange {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Iniciando agrupamiento por rangos de edad: " + filePath);
        long startTime = System.currentTimeMillis();
        Map<String, Integer> ageRanges = new HashMap<>();
        ageRanges.put("0-18", 0);
        ageRanges.put("19-30", 0);
        ageRanges.put("31-50", 0);
        ageRanges.put("51+", 0);
        int totalLines = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                String[] fields = line.split(",");
                if (fields.length >= 9) {
                    String birthDateStr = fields[3].trim(); // FechaNacimiento
                    try {
                        Date birthDate = sdf.parse(birthDateStr);
                        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        int age = Period.between(birthLocalDate, today).getYears();
                        if (age <= 18) {
                            ageRanges.put("0-18", ageRanges.get("0-18") + 1);
                        } else if (age <= 30) {
                            ageRanges.put("19-30", ageRanges.get("19-30") + 1);
                        } else if (age <= 50) {
                            ageRanges.put("31-50", ageRanges.get("31-50") + 1);
                        } else {
                            ageRanges.put("51+", ageRanges.get("51+") + 1);
                        }
                    } catch (ParseException e) {
                        // Fecha inválida, ignorar línea
                    }
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }

        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas procesadas: " + totalLines);
        log("Personas por rango de edad:");
        for (Map.Entry<String, Integer> entry : ageRanges.entrySet()) {
            log(entry.getKey() + ": " + entry.getValue());
        }
        log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");

    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
