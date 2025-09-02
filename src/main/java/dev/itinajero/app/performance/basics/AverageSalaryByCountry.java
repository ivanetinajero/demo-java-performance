package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AverageSalaryByCountry {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Iniciando cálculo de salario promedio por país: " + filePath);
        long startTime = System.currentTimeMillis();

        // Mapa para acumular la suma de salarios por país
        Map<String, Double> salarySum = new HashMap<>();
        // Mapa para contar la cantidad de personas por país
        Map<String, Integer> countryCount = new HashMap<>();
        int totalLines = 0;

        // Lectura del archivo línea por línea
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                // Separar los campos por coma
                String[] fields = line.split(",");
                // Validar que la línea tenga al menos 9 campos
                if (fields.length >= 9) {
                    String country = fields[8].trim(); // País (columna 9)
                    String salaryStr = fields[6].trim(); // Salario (columna 7)
                    try {
                        // Convertir el salario a double
                        double salary = Double.parseDouble(salaryStr);
                        // Sumar el salario al país correspondiente
                        salarySum.put(country, salarySum.getOrDefault(country, 0.0) + salary);
                        // Incrementar el contador de personas para el país
                        countryCount.put(country, countryCount.getOrDefault(country, 0) + 1);
                    } catch (NumberFormatException e) {
                        // Salario no válido, ignorar línea
                    }
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }

        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas procesadas: " + totalLines);
        log("Salario promedio por país:");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        // Calcular y mostrar el salario promedio por país
        for (String country : salarySum.keySet()) {
            double avg = salarySum.get(country) / countryCount.get(country);
            log(country + ": " + df.format(avg));
        }
        log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");

    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
