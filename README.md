# Java Performance Examples


Este repositorio contiene ejemplos prácticos para aprender y practicar conceptos de performance en Java, especialmente enfocados en la lectura y procesamiento de archivos de texto grandes.

## Ejemplos


### 1. Lectura de archivos línea por línea

**Clase:** `dev.itinajero.app.performance.basics.FileReadLineByLine`

**Descripción:**
Lee un archivo de texto línea por línea usando `BufferedReader`, cuenta el número de líneas y mide el tiempo total de lectura. Incluye logs con fecha y hora para cada proceso relevante.

**Conceptos clave:**
- Lectura eficiente de archivos grandes
- Uso de `BufferedReader`
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Uso:**
Modifica la variable `filePath` en la clase para especificar el archivo a leer (por ejemplo, `tmp/1m-registros-personas.txt`). Compila y ejecuta la clase para ver el rendimiento y los logs en consola.

---


### 2. Lectura de archivos con buffer de bytes y conteo de líneas

**Clase:** `dev.itinajero.app.performance.basics.FileReadWithBuffer`

**Descripción:**
Lee un archivo de texto usando un buffer de bytes (`FileInputStream`), suma la cantidad total de bytes leídos y cuenta el número de líneas detectando el carácter `\n` en el buffer. Mide el tiempo total de lectura y muestra logs con fecha y hora.

**Conceptos clave:**
- Lectura eficiente de archivos grandes usando bloques de bytes
- Uso de `FileInputStream` y buffers
- Conteo de líneas sin procesar texto
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Diferencias respecto a la lectura línea por línea:**
- Este método es más rápido porque lee grandes bloques de bytes y solo cuenta saltos de línea, sin convertir los datos a texto ni crear objetos String.
- Es ideal para operaciones simples (conteo de líneas, bytes, búsqueda de patrones simples), pero menos flexible para procesar el contenido de cada línea.

**Uso:**
Modifica la variable `filePath` en la clase para especificar el archivo a leer (por ejemplo, `tmp/1m-registros-personas.txt`). Compila y ejecuta la clase para ver el rendimiento, el conteo de líneas y los logs en consola.

---
