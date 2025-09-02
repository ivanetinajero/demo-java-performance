package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileReadLineByLine {

    public static void main(String[] args) {
        
        // Cambia esta variable para probar diferentes archivos
        String filePath = "tmp/1m-registros-personas.txt";
        int lineCount = 0;
        log("Iniciando lectura de archivo: " + filePath);
        long startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas leídas: " + lineCount);
        log("Tiempo total de lectura: " + (endTime - startTime) + " ms");
        
    }

    private static void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[" + timestamp + "] " + message);
    }

}
