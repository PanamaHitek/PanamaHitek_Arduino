# PanamaHitek_Arduino Library - Version 3.2.1

### Author: Antony García González
*Electromechanical Engineer, Teacher, and Researcher at the Technological University of Panama. Founder of the [Panama Hitek](http://panamahitek.com) project along with the creative team of Panama Hitek Creative Team.*

- **Email:** [antony.garcia.gonzalez@gmail.com](mailto:antony.garcia.gonzalez@gmail.com), [creativeteam@panamahitek.com](mailto:creativeteam@panamahitek.com)
- **Facebook:** [facebook.com/panamahitek](http://facebook.com/panamahitek)
- **Twitter:** [@panamahitek](http://twitter.com/panamahitek)
- **Instagram:** [panama_hitek](http://instagram.com/panama_hitek)

### Available Languages
- [English](README.md)
- [Español](README_es.md)

---

## General Description

The **PanamaHitek_Arduino** library provides a simple framework for handling serial communication between Java applications and Arduino boards. It offers three main classes:

- **`PanamaHitek_Arduino`** - Handles the establishment and management of serial connections and communication with Arduino boards.
- **`PanamaHitek_MultiMessage`** - Facilitates the reception and processing of multiple simultaneous messages from Arduino in Java.
- **`PanamaHitek_DataBuffer`** - Manages data organization, supports table visualization, and enables export to MS Excel (.xlsx) files.

### Additional Features

The library also includes four specialized classes for real-time graphical representation using the JFreeCharts dependency:

- **`PanamaHitek_DualDialChart`** - Creates Swing-based analog clock-type charts with two hands using JFreeCharts.
- **`PanamaHitek_SingleDialChart`** - Creates Swing-based analog clock-type charts with one hand using JFreeCharts.
- **`PanamaHitek_ThermometerChart`** - Generates Swing-based thermometer-type charts using JFreeCharts.
- **`PanamaHitek_TimeLineChart`** - Generates Swing-based multi-line charts based on time using JFreeCharts.

---

## Installation

### Prerequisites

- JDK 8 or higher (including JDK 21).
- Arduino IDE installed (optional, but recommended for simplifying the setup).

### Installation via Maven

Add the following lines to your `pom.xml` file:

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
### Latest version

[![](https://jitpack.io/v/PanamaHitek/PanamaHitek_Arduino.svg)](https://jitpack.io/#PanamaHitek/PanamaHitek_Arduino)

### Manual Installation

1. Download the latest version of the library from the [GitHub repository](https://github.com/PanamaHitek/PanamaHitek_Arduino/releases).
2. Extract the files to a directory of your choice.
3. Add the `.jar` files to your Java project classpath.

---

## Usage

To use the library effectively, ensure that the Arduino board is connected to the computer via USB. Additionally, the Arduino board must have an appropriate sketch uploaded to facilitate serial communication. The setup involves running an Arduino sketch on the board while a corresponding Java program operates on the computer.

The documentation includes several paired Arduino sketches and Java programs that can be used to test the library's functionality. You can perform the following tests:

- **Sending data from Java to Arduino**
  - Arduino Sketch: [`tx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/tx_example.ino)
  - Java Program: [`txExample.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/txExample.java)  
    _This Java program provides a Swing GUI with buttons to interact with the Arduino via serial communication._

- **Receiving data from Arduino to Java**
  - Arduino Sketch: [`single_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/single_data_send.ino)
  - Java Program: [`rxExample.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxExample.java)  
    _This Java script runs in the console and prints the received data._

- **Bidirectional communication between Java and Arduino**
  - Arduino Sketch: [`rxtx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/rxtx_example.ino)
  - Java Program: [`rxtxExample.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxtxExample.java)  
    _This Java program provides a Swing GUI with buttons to interact with the Arduino via serial communication._

- **Sending multiple data values from Arduino to Java**
  - Arduino Sketch: [`multiple_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/multiple_data_send.ino)
  - Java Program: [`rxMultiple.java`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxMultiple.java)  
    _This Java script runs in the console and prints multiple received data values._

For detailed information on each example, please refer to the [arduino_sketches folder in the repository](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/README.md).

---

## Version Updates

### Version 3.2.1
- Added support for the latest JSSC version to date (2.9.6), enabling compatibility with JDK 23, the most recent Java release.
- Expanded method-level documentation to include English alongside the existing Spanish documentation.
- The repository now officially supports bilingual documentation in both English and Spanish.

### Version 3.2.0
- Added support for JDK 11 and now fully compatible with the latest JDK 23, allowing backward compatibility.
- This update was made using a fork of JSSC available at: [GitHub Repository](https://github.com/java-native/jssc).

### Version 3.0.0
- New improved version of the library with additional features.
- Added tools to store received data and export it to MS Excel spreadsheets.
- Ability to generate real-time charts using POI and JFreeCharts libraries.
- Detailed usage examples have been added.

**Video tutorial (in Spanish):** [Tabulation and data export to Excel](https://www.youtube.com/watch?v=wo4ts0osZV8)

### Version 2.8.1
- Minor bug fixes present in version 2.8.0.

### Version 2.8.0
- Migration of the RXTX library to **Java Simple Serial Connector (JSSC)** by Alexey Sokolov to improve performance and compatibility across multiple platforms.
- Elimination of the need to manually install drivers for serial communication.
- Improved compatibility with Windows, Linux, macOS, and Solaris operating systems.
- Automation of the placement of necessary files in the operating system.

### Version 2.7.3
- Bug fixes for version 2.7.2.

### Version 2.7.2
- Added Maven compatibility to facilitate integration into Java projects.

### Version 2.7.1
- Minor internal structure bug fixes in the library.

### Version 2.7.0
- Before this version, it was necessary to have the `rxtxSerial.dll` drivers installed in the `JAVA_HOME` path.
- Starting from version 2.7.0, the library checks at each execution if the drivers are installed in the `C:/JavaRXTX` path.
- If the files do not exist, the library creates them automatically.
- This functionality **only works on Windows**. We invite Linux users to contribute to expand the capabilities to other platforms.

### Version 2.6.0
- The library was renamed from \\"Arduino for Java\\" to **PanamaHitek_Arduino**.
- Applied lower Camel Case to all methods.
- Methods renamed for greater consistency and clarity:
  - `NameSerialPortsAt()` is now `getSerialPorts()`.
  - `SerialPortsAvailable()` is now `getPortsAvailable()`.
  - `MessageAvailable()` is now `isMessageAvailable()`.
- Complete documentation of methods and classes with JavaDoc.
- Source code published on GitHub: [PanamaHitek Repository](https://github.com/PanamaHitek/PanamaHitek_Arduino).

### Version 2.5.0
- New methods added to the Arduino class:
  - **`void ShowMessageDialogs(boolean input)`**: Enables or disables pop-up windows when a runtime error occurs.
  - **`void SendByte(int input)`**: Sends bytes to Arduino through the serial port.

#### Previous Versions

The `Arduino` class includes the following methods for serial communication between Arduino and Java:

- **`void ArduinoTX(String PORT_NAME, int TIME_OUT, int DATA_RATE)`**  
  Establishes a serial connection to send data from Java to Arduino.

- **`void ArduinoRX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener event)`**  
  Sets up a connection to receive data from Arduino to Java using an event listener.

- **`void ArduinoRXTX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener event)`**  
  Enables bidirectional communication between Arduino and Java, allowing both sending and receiving data.

- **`void SendData(String data)`**  
  Sends a string of text from Java to Arduino.

- **`String ReceiveData()`**  
  Receives data directly from Arduino as a string of text.

- **`boolean MessageAvailable()`**  
  Returns `true` if a message has been received from Arduino, indicating data availability.

- **`String PrintMessage()`**  
  Retrieves the message received from Arduino as a string of text.

- **`int SerialPortsAvailable()`**  
  Returns the number of serial ports available on the system.

- **`String NameSerialPortAt(int index)`**  
  Returns the name of the serial port corresponding to the specified index.

- **`void KillArduinoConnection()`**  
  Terminates the established connection between Arduino and Java, releasing the resources used.


---

## Documentation

For complete documentation and usage examples, visit:  
[PanamaHitek Arduino Library](http://panamahitek.com/libreria-arduino-para-java/)