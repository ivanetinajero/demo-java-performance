package dev.itinajero.app.performance.basics;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileReadWithBuffer {

    public static void main(String[] args) {

        String filePath = "tmp/1m-registros-personas.txt"; // Cambia el archivo según lo necesites
        int bufferSize = 8192; // 8 KB
        log("Iniciando lectura de archivo con buffer de bytes: " + filePath);
        long startTime = System.currentTimeMillis();
        int totalBytes = 0;
        int lineCount = 0;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                totalBytes += bytesRead;
                for (int i = 0; i < bytesRead; i++) {
                    if (buffer[i] == '\n') {
                        lineCount++;
                    }
                }
            }
        } catch (IOException e) {
            log("Error al leer el archivo: " + e.getMessage());
            return;
        }
        long endTime = System.currentTimeMillis();
        log("Lectura finalizada. Bytes leídos: " + totalBytes);
        log("Líneas encontradas: " + lineCount);
        log("Tiempo total de lectura: " + (endTime - startTime) + " ms");

    }

    private static void log(String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        System.out.println("[" + timestamp + "] " + message);
    }

}
