package dev.itinajero.app.performance.basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadFromLineNumber {
    public static void main(String[] args) {

        String filePath = "tmp/10m-registros-personas.txt"; // Ruta del archivo a leer
        int startLine = 10000; // Número de línea desde donde quieres empezar a leer (1-indexado)
        log("Iniciando lectura desde la línea " + startLine + ": " + filePath);
        long startTime = System.currentTimeMillis();
        int currentLine = 0; // Contador de líneas leídas (incluyendo las saltadas)
        int linesRead = 0;   // Contador de líneas procesadas después del salto

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Saltar las primeras (startLine-1) líneas leyendo y descartando
            while (currentLine < startLine - 1 && (line = br.readLine()) != null) {
                currentLine++;
            }
            // Leer y mostrar las siguientes 10 líneas como ejemplo
            // Aquí es donde realmente empieza el procesamiento que te interesa
            while ((line = br.readLine()) != null && linesRead < 10) {
                log("Línea " + (currentLine + 1) + ": " + line); // Muestra la línea actual
                currentLine++;
                linesRead++;
            }
            // Si quisieras procesar hasta el final, elimina la condición 'linesRead < 10'
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }

        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Líneas leídas desde la línea " + startLine + ": " + linesRead);
        log("Tiempo total de lectura: " + (endTime - startTime) + " ms");
    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
