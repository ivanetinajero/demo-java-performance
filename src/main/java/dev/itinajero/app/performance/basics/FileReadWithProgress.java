package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileReadWithProgress {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
        log("Contando el total de líneas del archivo...");
        int totalLines = 0;
        
        // Primer paso: contar el total de líneas
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.readLine() != null) {
                totalLines++;
            }
        } catch (IOException e) {
            log("Error al contar líneas: " + e.getMessage());
            return;
        }
        log("Total de líneas: " + totalLines);

        log("Iniciando procesamiento con avance en porcentaje...");
        long startTime = System.currentTimeMillis();
        int currentLine = 0;
        int lastPercent = 0;

        // Segundo paso: procesar el archivo mostrando el avance
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                currentLine++;
                // Calcular el porcentaje de avance como entero
                int percent = (int) ((currentLine * 100L) / totalLines);
                // Mostrar el avance solo cuando sea múltiplo de 10% y no se haya mostrado antes
                // Así se imprime: 10%, 20%, ..., 100%
                if (percent % 10 == 0 && percent != lastPercent) {
                    log("Avance: " + percent + "%");
                    lastPercent = percent;
                }
                // Aquí podrías procesar la línea si lo deseas
            }
        } catch (IOException e) {
            log("Error al procesar el archivo: " + e.getMessage());
            return;
        }

        long endTime = System.currentTimeMillis();
        log("Procesamiento finalizado. Líneas leídas: " + currentLine);
        log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");

    }

    // Método para imprimir logs con timestamp
    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
