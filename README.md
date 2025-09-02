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


### 3. Conteo de personas por país

**Clase:** `dev.itinajero.app.performance.basics.CountPeopleByCountry`

**Descripción:**
Lee un archivo de texto línea por línea usando `BufferedReader`, separa los campos de cada línea y cuenta cuántas personas hay por país usando un `HashMap`. Mide el tiempo total de procesamiento y muestra logs con fecha y hora.

**Conceptos clave:**
- Procesamiento de archivos grandes con lógica de negocio
- Uso de `BufferedReader` y `HashMap`
- Parsing de texto y separación de campos
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Resultados de performance:**
- Para un archivo de 10 millones de registros, el conteo por país tomó aproximadamente 11.4 segundos (11431 ms).
- Solo contar líneas (sin procesamiento adicional) en el mismo archivo tomó aproximadamente 5 segundos (4997 ms).
- Esto demuestra cómo el procesamiento extra (split, trim, conteo en mapa) impacta el tiempo total.

**Uso:**
Modifica la variable `filePath` en la clase para especificar el archivo a leer (por ejemplo, `tmp/10m-registros-personas.txt`). Compila y ejecuta la clase para ver el conteo por país, el total de líneas y los logs en consola.



### 4. Salario promedio por país

**Clase:** `dev.itinajero.app.performance.basics.AverageSalaryByCountry`

**Descripción:**
Lee un archivo de texto línea por línea usando `BufferedReader`, separa los campos de cada línea, acumula la suma de salarios y el conteo de personas por país usando dos mapas (`HashMap`). Al final, calcula y muestra el salario promedio por país. Incluye logs con fecha y hora y mide el tiempo total de procesamiento.

**Conceptos clave:**
- Procesamiento numérico y agrupamiento por clave
- Uso de `BufferedReader` y `HashMap`
- Parsing de texto, manejo de errores numéricos
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Uso:**
Modifica la variable `filePath` en la clase para especificar el archivo a leer (por ejemplo, `tmp/1m-registros-personas.txt`). Compila y ejecuta la clase para ver el salario promedio por país y los logs en consola.



### 5. Lectura desde un número de línea específico

**Clase:** `dev.itinajero.app.performance.basics.ReadFromLineNumber`

**Descripción:**
Permite saltar las primeras N líneas de un archivo de texto y comenzar a leer desde la línea que indiques. El ejemplo muestra cómo descartar líneas hasta llegar a la deseada y luego procesar (o mostrar) las siguientes líneas. Incluye logs con fecha y hora y mide el tiempo total de lectura.

**Conceptos clave:**
- Salto de líneas en archivos de texto
- Uso de `BufferedReader`
- Control de flujo y contadores de línea
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Notas:**
- Si el número de línea de inicio es muy alto, el programa igualmente debe leer y descartar todas las líneas anteriores una por una.
- Si quieres procesar hasta el final del archivo, elimina la condición que limita la cantidad de líneas leídas después del salto.

**Uso:**
Modifica la variable `filePath` para el archivo a leer y `startLine` para indicar desde qué línea comenzar. Compila y ejecuta la clase para ver el resultado y los logs en consola.



### 6. Lectura con avance en porcentaje

**Clase:** `dev.itinajero.app.performance.basics.FileReadWithProgress`

**Descripción:**
Lee un archivo de texto línea por línea, mostrando en consola el avance en porcentaje (por ejemplo, 10%, 20%, ..., 100%) conforme se procesa el archivo. Primero cuenta el total de líneas y luego procesa el archivo mostrando el progreso. Incluye logs con fecha y hora y mide el tiempo total de procesamiento.

**Conceptos clave:**
- Lectura eficiente de archivos grandes
- Cálculo y visualización de progreso
- Uso de `BufferedReader`
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Notas:**
- El avance se muestra solo en múltiplos de 10% para evitar saturar la consola.
- Es necesario recorrer el archivo dos veces: una para contar líneas y otra para procesar mostrando el avance.

**Uso:**
Modifica la variable `filePath` en la clase para especificar el archivo a leer. Compila y ejecuta la clase para ver el avance en porcentaje y los logs en consola.



### 7. Agrupamiento por rangos de edad

**Clase:** `dev.itinajero.app.performance.basics.GroupByAgeRange`

**Descripción:**
Lee un archivo de texto línea por línea usando `BufferedReader`, calcula la edad de cada persona a partir de la fecha de nacimiento y agrupa el conteo en rangos de edad: 0-18, 19-30, 31-50 y 51+. Incluye logs con fecha y hora y mide el tiempo total de procesamiento.

**Conceptos clave:**
- Procesamiento de fechas y cálculo de edad
- Agrupamiento y conteo por rangos
- Uso de `BufferedReader`, `SimpleDateFormat` y `java.time`
- Medición de tiempos de ejecución
- Buenas prácticas de logging

**Resultados de performance:**
- Para un archivo de 10 millones de registros:
	- 0-18: 232,582
	- 19-30: 2,791,376
	- 31-50: 4,649,905
	- 51+: 2,326,137
	- Tiempo total de procesamiento: 23,559 ms

**Uso:**
Modifica la variable `filePath` en la clase para especificar el archivo a leer. Compila y ejecuta la clase para ver el conteo por rangos de edad y los logs en consola.

---


