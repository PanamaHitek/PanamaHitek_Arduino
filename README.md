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

- JDK 8 o superior (JDK 11 recomendado).
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
- Se agregó soporte para JDK 11, lo que permite la compatibilidad más allá de JDK 1.8.
- Esta actualización se realizó utilizando un fork de JSSC disponible en: [Repositorio GitHub](https://github.com/java-native/jssc).

### Versión 3.0.0
- Mejora en las capacidades de almacenamiento de datos.
- Nueva funcionalidad para exportar datos a MS Excel y generación de gráficos en tiempo real utilizando POI y JFreeCharts.
- Se agregaron ejemplos de uso detallados.

**Video tutorial:** [Tabulación y exportación de datos a Excel](https://www.youtube.com/watch?v=wo4ts0osZV8)

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
