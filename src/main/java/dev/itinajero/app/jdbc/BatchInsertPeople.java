package dev.itinajero.app.jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ejemplo de carga masiva (batch insert) de personas a MySQL usando JDBC puro.
  
   Estructura esperada de la tabla:
 
   CREATE TABLE Personas (
      id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
      nombre VARCHAR(150) NOT NULL,
      apellido VARCHAR(150) NOT NULL,
      email VARCHAR(150) NOT NULL,
      fechaNacimiento DATE NOT NULL,
      estadoCivil VARCHAR(50) NOT NULL,
      profesion VARCHAR(150) NOT NULL,
      salario DOUBLE NOT NULL,
      peso DOUBLE NOT NULL,
      pais VARCHAR(150) NOT NULL
   );
  
   Ejemplo de líneas en el archivo:
   FELICIANO,TUYIN,felicianotuyin@instructor.net,1974-11-08,Casado(a),Ladrillero,9000,56.9,Reino Unido
   CLAUDIA SILVIA,HOMA,claudiasilvia.h@groupmail.com,2007-05-01,Casado(a),Coach,10600,94.6,Líbano
 */
public class BatchInsertPeople {
   public static void main(String[] args) throws Exception {
      // Ruta del archivo de datos a cargar
      String filePath = "tmp/10m-registros-personas.txt"; // Cambia el archivo según lo necesites
      // Datos de conexión a MySQL
      String url = "jdbc:mysql://127.0.0.1:3308/test?useSSL=false&serverTimezone=America/Mexico_City&allowPublicKeyRetrieval=true";
      String user = "root";
      String password = "admin"; // Deja vacío si no hay password, o pon el tuyo

      // Tamaño del batch: cantidad de registros que se insertan antes de hacer commit. Un valor mayor reduce la cantidad de viajes a la base y mejora el
      // rendimiento, pero si es demasiado grande puede agotar la memoria o saturar buffers. Prueba con 1000, 2000, 5000, 10000 y compara tiempos.
      int batchSize = 5000;

      log("Iniciando carga masiva a MySQL: " + filePath);
      long startTime = System.currentTimeMillis();
      int total = 0;

      // Se abre una sola conexión a la base de datos para todo el proceso de carga masiva. Gracias al try-with-resources, la conexión se cierra automáticamente 
      // al finalizar el bloque, asegurando que todos los inserts y commits se hagan sobre la misma conexión abierta.
      try (Connection conn = DriverManager.getConnection(url, user, password)) {
         // Desactivar auto-commit permite agrupar muchos inserts en una sola transacción y hacer commit por lote (batch), lo que es mucho más eficiente 
         // para cargas masivas. Si dejaras auto-commit en true, cada insert haría un commit individual y sería mucho más lento.
         conn.setAutoCommit(false);
         
         String sql = "INSERT INTO Personas (nombre, apellido, email, fechaNacimiento, estadoCivil, profesion, salario, peso, pais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         
         // Ambos recursos (PreparedStatement y BufferedReader) se declaran dentro del mismo try-with-resources, separados por ';'. 
         // Al finalizar el bloque try, Java cierra automáticamente ambos recursos, en orden inverso al que fueron declarados.
         try (PreparedStatement ps = conn.prepareStatement(sql); BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
               // Separa los campos por coma, según la estructura del archivo
               String[] fields = line.split(",");
               if (fields.length >= 9) {
                  // Asigna cada campo a su parámetro en el PreparedStatement
                  ps.setString(1, fields[0].trim()); // nombre
                  ps.setString(2, fields[1].trim()); // apellido
                  ps.setString(3, fields[2].trim()); // email
                  ps.setString(4, fields[3].trim()); // fecha_nacimiento (yyyy-MM-dd)
                  ps.setString(5, fields[4].trim()); // estado_civil
                  ps.setString(6, fields[5].trim()); // profesion
                  ps.setBigDecimal(7, new java.math.BigDecimal(fields[6].trim())); // salario
                  ps.setBigDecimal(8, new java.math.BigDecimal(fields[7].trim())); // peso
                  ps.setString(9, fields[8].trim()); // pais
                  ps.addBatch();
                  count++;
                  total++;
                  // Cada vez que se llena el batch, se ejecuta y se hace commit
                  if (count % batchSize == 0) {
                     ps.executeBatch();
                     conn.commit();
                     log("Registros insertados: " + total);
                  }
               }
            }
            // Este bloque es fundamental para no perder registros:
            // Si el total de registros no es múltiplo exacto de batchSize, al salir del ciclo pueden quedar registros pendientes en el batch.
            // Por ejemplo, con batchSize=1000 y 2505 registros, se insertan 2000 en el ciclo, pero quedan 505 que solo se insertan si ejecutamos este bloque final.
            // Sin esto, esos últimos registros no se guardarían en la base de datos.
            if (count % batchSize != 0) {
               ps.executeBatch();
               conn.commit();
            }
         }
      } catch (SQLException e) {
         log("Error SQL: " + e.getMessage());
         throw e;
      }
      long endTime = System.currentTimeMillis();
      log("Carga finalizada. Total insertados: " + total);
      log("Tiempo total de procesamiento: " + (endTime - startTime) + " ms");
      log("\nTips de performance:");
      log("- Usa batchSize grandes (2000, 5000, 10000) para mejorar velocidad, pero cuida la memoria.");
      log("- Deja auto-commit en false para cargas masivas, así reduces commits y aceleras el proceso.");
      log("- Si la tabla tiene índices o claves foráneas, desactívalos temporalmente para cargas muy grandes.");
      log("- LOAD DATA INFILE de MySQL es aún más rápido, pero requiere permisos y formato especial.");
   }

   private static void log(String message) {
      String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
      System.out.println("[" + timestamp + "] " + message);
   }

}
