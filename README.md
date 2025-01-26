# Biblioteca PanamaHitek_Arduino - Versión 3.2.0

## Autor: Antony García González
*Ingeniero Electromecánico, Docente e Investigador en la Universidad Tecnológica de Panamá. Fundador del proyecto [Panama Hitek](http://panamahitek.com) junto al equipo creativo de Panama Hitek.*

- **Correo electrónico:** antony.garcia.gonzalez@gmail.com  
- **Facebook:** [facebook.com/panamahitek](http://facebook.com/panamahitek)  
- **Twitter:** [@panamahitek](http://twitter.com/panamahitek)

---

## Descripción general

La biblioteca **PanamaHitek_Arduino** proporciona un marco robusto para manejar la comunicación serial entre aplicaciones Java y placas Arduino. Ofrece tres clases principales:

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

- JDK 8 o superior (incluyendo la última versión JDK 23).
- Arduino IDE instalado (opcional, pero recomendado para simplificar la configuración).

### Instalación mediante Maven

Agrega las siguientes líneas a tu archivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.github.PanamaHitek</groupId>
        <artifactId>PanamaHitek_Arduino</artifactId>
        <version>2.7.2</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Instalación manual

1. Descarga la última versión de la biblioteca desde el [repositorio de GitHub](https://github.com/PanamaHitek/Arduino-JavaAPI).
2. Extrae los archivos en un directorio de tu preferencia.
3. Añade los archivos `.jar` al classpath de tu proyecto Java.

---

## Actualizaciones de versión

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
- Migración de la librería RXTX a **Java Simple Serial Connector (JSSC)** de Alexey Sokolov para una mejor performance y compatibilidad con múltiples plataformas.
- Eliminación de la necesidad de instalación manual de drivers para la comunicación serial.
- Compatibilidad mejorada con sistemas operativos Windows, Linux, macOS y Solaris.
- Automatización de la ubicación de archivos necesarios en el sistema operativo.

### Versión 2.7.3
- Corrección de errores de la versión 2.7.2.

### Versión 2.7.2
- Incorporación de compatibilidad con Maven para facilitar la integración en proyectos Java.

### Versión 2.7.1
- Corrección de pequeños errores en la estructura interna de la librería.

### Versión 2.7.0
- Antes de esta versión era necesario tener instalados los drivers `rxtxSerial.dll` en la ruta de `JAVA_HOME`.
- A partir de la versión 2.7.0, en cada ejecución la librería verifica si los drivers están instalados en la ruta `C:/JavaRXTX`.
- Si no existen dichos ficheros, la librería los crea automáticamente.
- Este feature **solo funciona en Windows**. Invitamos a los usuarios de Linux a contribuir para expandir las capacidades a otras plataformas.
- Repositorio oficial en GitHub: [PanamaHitek Arduino Java API](https://github.com/PanamaHitek/Arduino-JavaAPI).

### Versión 2.6.0
- La librería fue renombrada de "Arduino para Java" a **PanamaHitek_Arduino**.
- Aplicación del lower Camel Case a todos los métodos.
- Métodos renombrados para mayor coherencia y claridad:
  - `NameSerialPortsAt()` ahora es `getSerialPorts()`.
  - `SerialPortsAvailable()` ahora es `getPortsAvailable()`.
  - `MessageAvailable()` ahora es `isMessageAvailable()`.
- Documentación completa de métodos y clases con JavaDoc.
- Publicación del código fuente en GitHub: [Repositorio PanamaHitek](https://github.com/PanamaHitek/Arduino-JavaAPI).

### Versión 2.5.0
- Se incluyen nuevos métodos en la clase Arduino:
  - **`void ShowMessageDialogs(boolean input)`**: Permite activar o desactivar las ventanas emergentes cuando se produce algún error en tiempo de ejecución.
  - **`void SendByte(int input)`**: Envía Bytes a Arduino por medio del puerto Serie.

#### Versiones Anteriores
- Métodos incluidos en la clase Arduino:
  - **`void ArduinoTX(String PORT_NAME, int TIME_OUT, int DATA_RATE)`**: Establece una conexión entre Arduino y Java para enviar datos de Java a Arduino por comunicación serial.
  - **`void ArduinoRX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)`**: Establece una conexión para recibir datos de Arduino a Java.
  - **`void ArduinoRXTX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)`**: Permite comunicación bidireccional entre Arduino y Java.
  - **`void SendData(String data)`**: Envía una cadena de caracteres desde Java hacia Arduino.
  - **`String ReceiveData()`**: Recibe información directamente desde Arduino.
  - **`boolean MessageAvailable()`**: Devuelve `true` cuando se ha recibido un mensaje desde Arduino.
  - **`String PrintMessage()`**: Devuelve un string con el mensaje enviado desde Arduino.
  - **`int SerialPortsAvailable()`**: Devuelve la cantidad de puertos serie disponibles.
  - **`String NameSerialPortAt(int index)`**: Nombra los puertos serie disponibles.
  - **`void KillArduinoConnection()`**: Finaliza la conexión entre Arduino y Java.

---

## Métodos principales

### Clase: `PanamaHitek_Arduino`

- **`void ArduinoTX(String PORT_NAME, int TIME_OUT, int DATA_RATE)`**
  - Establece una conexión para enviar datos de Java a Arduino.
- **`void ArduinoRX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)`**
  - Permite recibir datos de Arduino a Java.
- **`void ArduinoRXTX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)`**
  - Habilita la comunicación bidireccional.
- **`void SendData(String data)`**
  - Envía una cadena de caracteres a Arduino.
- **`String ReceiveData()`**
  - Recibe datos desde Arduino.
- **`boolean isMessageAvailable()`**
  - Verifica si un mensaje ha sido recibido.
- **`void KillArduinoConnection()`**
  - Finaliza la conexión.

---

## Documentación

Para documentación completa y ejemplos de uso, visita:  
[PanamaHitek Arduino Library](http://panamahitek.com/libreria-arduino-para-java/)
