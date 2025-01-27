# Ejemplos de Sketches de Arduino

Esta carpeta contiene una colección de sketches de Arduino que muestran varias funcionalidades de la biblioteca **PanamaHitek_Arduino**.
Estos sketches están específicamente diseñados para integrarse con software Java, requiriendo ciertos
programas para ejecutarse en el Arduino y habilitar la comunicación serial entre el microcontrolador
y la aplicación Java.
## Resumen de Sketches

### 1. [`rxtx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/rxtx_example.ino)

**Descripción:**
- Lee datos entrantes del puerto serial.
- Convierte los datos recibidos a un número de punto flotante.
- Calcula la raíz cuadrada del número.
- Envía el resultado calculado de vuelta a través del puerto serial.

**Uso:**  
Utiliza este sketch para probar la transmisión y recepción de datos seriales en tu software Java. Está diseñado para probar
[rxtxExample.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxtxExample.java),
que es una aplicación Swing que envía un número al Arduino, recibe la raíz cuadrada de ese número y la muestra en una etiqueta.

La interfaz gráfica de Swing se ve así:

<p align="center">
  <img src="https://raw.githubusercontent.com/PanamaHitek/PanamaHitek_Arduino/refs/heads/master/src/main/resources/images/rxtxExample.png" alt="rxtxExample">
</p>

Se ingresa un número en el campo de texto, y el botón "Calcular" envía el número al Arduino. La raíz cuadrada del número se muestra en la etiqueta de abajo (Raíz Cuadrada).

---

### 2. [`single_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/single_data_send.ino)

**Descripción:**  
Este sketch envía un valor entero único a través de la comunicación serial. El entero se incrementa con cada transmisión.

**Funciones:**
- `setup()`: Inicializa la comunicación serial a una velocidad de 9600 baudios.
- `loop()`: Envía el valor de la variable `i` a través del puerto serial y lo incrementa cada segundo.

**Uso:**  
Utiliza este sketch para probar la recepción de datos seriales en tu software Java observando los valores incrementales.
Este sketch está diseñado para probar la recepción de un valor de datos único con el programa [rxSimple.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxSimple.java)
ejecutándose en el lado de Java.

---

### 3. [`tx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/tx_example.ino)

**Descripción:**  
Este sketch controla un LED conectado al pin 13 del Arduino basado en comandos seriales.
Escucha mensajes específicos y responde en consecuencia.

**Funcionalidad:**
- Lee datos entrantes del puerto serial.
- Enciende el LED al recibir el comando `"on"`.
- Apaga el LED al recibir el comando `"off"`.

**Uso:**  
Para controlar el LED, envía los comandos apropiados ("on"/"off") a través del monitor serial o una aplicación Java.
Este sketch está diseñado para probar la transmisión de datos desde el lado de Java usando el programa
[txExample.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/txExample.java).
Este programa renderiza una interfaz gráfica de Swing con dos botones para controlar el LED, como se muestra en la imagen a continuación:

<p align="center">
  <img src="https://raw.githubusercontent.com/PanamaHitek/PanamaHitek_Arduino/refs/heads/master/src/main/resources/images/txExample.png" alt="rxtxExample">
</p>

El botón "Encender" envía el comando "on" al Arduino, encendiendo el LED. El botón "Apagar" envía el comando "off" al Arduino, apagando el LED.

---

### 4. [`multiple_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/multiple_data_send.ino)

**Descripción:**
Este sketch envía múltiples valores de datos a través de la comunicación serial. Los valores se envían utilizando la función Serial.println(),
que añade un carácter de nueva línea a cada valor. De esa manera, la aplicación Java puede leer los valores línea por línea y analizarlos en consecuencia.

**Funcionalidad:**
- Envía cuatro valores (enteros) a través de la comunicación serial.
- Los valores se inicializan en 0, 10, 100 y 1000.
- Los valores se incrementan en 1 con cada transmisión.

**Uso:**
Utiliza este sketch para probar la recepción de múltiples valores de datos en tu software Java.
Este sketch está diseñado para probar la recepción de múltiples valores de datos con el programa
[rxMultiple.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxMultiple.java),
que establece la comunicación serial con el Arduino y lee los valores enviados por el Arduino y los muestra en la consola.

---

### 5. [`double_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/double_data_send.ino)

**Descripción:**
Este sketch envía dos valores enteros a través de la comunicación serial. Los valores se envían utilizando la función Serial.println(),
que añade un carácter de nueva línea a cada valor. De esa manera, la aplicación Java puede leer los valores línea por línea y analizarlos en consecuencia.

**Funcionalidad:**
- Envía dos valores (enteros) a través de la comunicación serial.
- Los valores se inicializan en 0 y 10.
- Los valores se incrementan en 1 con cada transmisión.

**Uso:**
Utiliza este sketch para probar la recepción de dos valores de datos en tu software Java.




