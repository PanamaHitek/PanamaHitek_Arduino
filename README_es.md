# Biblioteca PanamaHitek_Arduino - Versión 3.2.1

### Autor: Antony García González
*Ingeniero Electromecánico, Docente e Investigador en la Universidad Tecnológica de Panamá. Fundador del proyecto [Panama Hitek](http://panamahitek.com) junto con el Panama Hitek Creative Team.*

- **Email:** [antony.garcia.gonzalez@gmail.com](mailto:antony.garcia.gonzalez@gmail.com), [creativeteam@panamahitek.com](mailto:creativeteam@panamahitek.com)
- **Facebook:** [facebook.com/panamahitek](http://facebook.com/panamahitek)
- **Twitter:** [@panamahitek](http://twitter.com/panamahitek)
- **Instagram:** [panama_hitek](http://instagram.com/panama_hitek)

### Idiomas disponibles
- [Inglés](README.md)
- [Español](README_es.md)

---

## Descripción general

La biblioteca **PanamaHitek_Arduino** proporciona una herramienta simple para manejar la comunicación serial entre aplicaciones Java y placas Arduino. Ofrece tres clases principales:

- **`PanamaHitek_Arduino`** - Administra las conexiones y la comunicación con Arduino.
- **`PanamaHitek_MultiMessage`** - Permite la recepción simultánea de múltiples mensajes en Java.
- **`PanamaHitek_DataBuffer`** - Organiza datos, facilita la visualización en tabla y permite la exportación a MS Excel (.xlsx).

### Características adicionales

La biblioteca también incluye cuatro clases especializadas para la representación gráfica en tiempo real utilizando la dependencia JFreeCharts:

- **`PanamaHitek_DualDialChart`** - Crea gráficos tipo reloj analógico con dos agujas.
- **`PanamaHitek_SingleDialChart`** - Crea gráficos tipo reloj analógico con una aguja.
- **`PanamaHitek_ThermometerChart`** - Genera gráficos tipo termómetro.
- **`PanamaHitek_TimeLineChart`** - Genera gráficos de líneas múltiples en función del tiempo.

---

## Instalación

### Requisitos previos

- JDK 8 o superior (incluyendo la versión JDK 21).
- Arduino IDE instalado (opcional, pero recomendado para simplificar la configuración).

### Instalación mediante Maven

Agrega las siguientes líneas a tu archivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.github.PanamaHitek</groupId>
        <artifactId>PanamaHitek_Arduino</artifactId>
        <version>3.2.1</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
### Versión más reciente

[![](https://jitpack.io/v/PanamaHitek/PanamaHitek_Arduino.svg)](https://jitpack.io/#PanamaHitek/PanamaHitek_Arduino)

### Instalación manual

1. Descarga la última versión de la biblioteca desde el [repositorio de GitHub](https://github.com/PanamaHitek/Arduino-JavaAPI).
2. Extrae los archivos en un directorio de tu preferencia.
3. Añade los archivos `.jar` al classpath de tu proyecto Java.

---

## Uso

Para utilizar la biblioteca de manera efectiva, asegúrate de que la placa Arduino esté conectada a la computadora a través de USB. Además, la placa Arduino debe tener un sketch apropiado cargado para facilitar la comunicación serial. La configuración implica ejecutar un sketch de Arduino en la placa mientras un programa Java correspondiente opera en la computadora.

La documentación incluye varios sketches de Arduino emparejados con programas Java que se pueden usar para probar la funcionalidad de la biblioteca. Puedes realizar las siguientes pruebas:

- **Enviar datos de Java a Arduino**
  - Sketch de Arduino: [`tx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/tx_example.ino)
  - Programa Java: [`txExample.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/txExample.java)  
    _Este programa Java proporciona una GUI de Swing con botones para interactuar con el Arduino a través de la comunicación serial._

- **Recibir datos de Arduino a Java**
  - Sketch de Arduino: [`single_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/single_data_send.ino)
  - Programa Java: [`rxExample.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxSimple.java)  
    _Este script Java se ejecuta en la consola e imprime los datos recibidos._

- **Comunicación bidireccional entre Java y Arduino**
  - Sketch de Arduino: [`rxtx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/rxtx_example.ino)
  - Programa Java: [`rxtxExample.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxtxExample.java)  
    _Este programa Java proporciona una GUI de Swing con botones para interactuar con el Arduino a través de la comunicación serial._

- **Enviar múltiples valores de datos de Arduino a Java**
  - Sketch de Arduino: [`multiple_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/multiple_data_send.ino)
  - Programa Java: [`rxMultiple.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxMultiple.java)  
    _Este script Java se ejecuta en la consola e imprime múltiples valores de datos recibidos._

Para información detallada sobre cada ejemplo, por favor consulta la [carpeta arduino_sketches en el repositorio](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/README_es.md).

---

## Actualizaciones de versión

### Versión 3.2.1
- Se agregó soporte para la última versión de JSSC hasta la fecha (2.9.6), permitiendo la compatibilidad con JDK 23, la versión más reciente de Java.
- Se amplió la documentación a nivel de métodos para incluir inglés junto con la documentación existente en español.
- El repositorio ahora admite oficialmente documentación bilingüe en inglés y español.

### Versión 3.2.0
- Se agregó soporte para JDK 11 y ahora es totalmente compatible con la última versión de JDK 23, permitiendo la compatibilidad con versiones anteriores.
- Esta actualización se realizó utilizando un fork de JSSC disponible en: [Repositorio GitHub](https://github.com/java-native/jssc).

### Versión 3.0.0
- Nueva versión mejorada de la librería con funcionalidades adicionales.
- Incorporación de herramientas para almacenar datos recibidos y exportarlos a hojas de cálculo de MS Excel.
- Capacidad para generar gráficos en tiempo real mediante las bibliotecas POI y JFreeCharts.
- Se han agregado ejemplos de uso detallados.

**Video tutorial:** [Tabulación y exportación de datos a Excel](https://www.youtube.com/watch?v=wo4ts0osZV8)

### Versión 2.8.1
- Corrección de errores menores presentes en la versión 2.8.0.

### Versión 2.8.0
- Migración de la biblioteca RXTX a **Java Simple Serial Connector (JSSC)** de Alexey Sokolov para mejorar el rendimiento y la compatibilidad con múltiples plataformas.
- Eliminación de la necesidad de instalar manualmente los controladores para la comunicación serial.
- Compatibilidad mejorada con los sistemas operativos Windows, Linux, macOS y Solaris.
- Automatización de la ubicación de los archivos necesarios en el sistema operativo.

### Versión 2.7.3
- Corrección de errores de la versión 2.7.2.

### Versión 2.7.2
- Incorporación de compatibilidad con Maven para facilitar la integración en proyectos Java.

### Versión 2.7.1
- Corrección de pequeños errores en la estructura interna de la biblioteca.

### Versión 2.7.0
- Antes de esta versión, era necesario tener instalados los drivers `rxtxSerial.dll` en la ruta de `JAVA_HOME`.
- A partir de la versión 2.7.0, la biblioteca verifica en cada ejecución si los drivers están instalados en la ruta `C:/JavaRXTX`.
- Si los archivos no existen, la biblioteca los crea automáticamente.
- Esta funcionalidad **solo funciona en Windows**. Invitamos a los usuarios de Linux a contribuir para expandir las capacidades a otras plataformas.

### Versión 2.6.0
- La biblioteca fue renombrada de \\"Arduino para Java\\" a **PanamaHitek_Arduino**.
- Aplicación de lower Camel Case a todos los métodos.
- Métodos renombrados para mayor coherencia y claridad:
  - `NameSerialPortsAt()` ahora es `getSerialPorts()`.
  - `SerialPortsAvailable()` ahora es `getPortsAvailable()`.
  - `MessageAvailable()` ahora es `isMessageAvailable()`.
- Documentación completa de métodos y clases con JavaDoc.
- Publicación del código fuente en GitHub: [Repositorio PanamaHitek](https://github.com/PanamaHitek/PanamaHitek_Arduino).

### Versión 2.5.0
- Nuevos métodos añadidos a la clase Arduino:
  - **`void ShowMessageDialogs(boolean input)`**: Activa o desactiva las ventanas emergentes cuando ocurre un error en tiempo de ejecución.
  - **`void SendByte(int input)`**: Envía bytes a Arduino a través del puerto serie.

#### Versiones Anteriores

La clase `Arduino` incluye los siguientes métodos para la comunicación serial entre Arduino y Java:

- **`void ArduinoTX(String PORT_NAME, int TIME_OUT, int DATA_RATE)`**  
  Establece una conexión serial para enviar datos desde Java hacia Arduino.

- **`void ArduinoRX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)`**  
  Configura una conexión para recibir datos desde Arduino hacia Java mediante un evento de escucha.

- **`void ArduinoRXTX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)`**  
  Habilita la comunicación bidireccional entre Arduino y Java, permitiendo tanto el envío como la recepción de datos.

- **`void SendData(String data)`**  
  Envía una cadena de texto desde Java hacia Arduino.

- **`String ReceiveData()`**  
  Recibe datos directamente desde Arduino en forma de cadena de texto.

- **`boolean MessageAvailable()`**  
  Retorna `true` si hay un mensaje recibido desde Arduino, indicando disponibilidad de datos.

- **`String PrintMessage()`**  
  Obtiene el mensaje recibido desde Arduino en forma de cadena de texto.

- **`int SerialPortsAvailable()`**  
  Devuelve el número de puertos serial disponibles en el sistema.

- **`String NameSerialPortAt(int index)`**  
  Retorna el nombre del puerto serial correspondiente al índice especificado.

- **`void KillArduinoConnection()`**  
  Finaliza la conexión establecida entre Arduino y Java, liberando los recursos utilizados.


---

## Documentación

Para documentación completa y ejemplos de uso, visita:  
[PanamaHitek Arduino Library](http://panamahitek.com/libreria-arduino-para-java/)
