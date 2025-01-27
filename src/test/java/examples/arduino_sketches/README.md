# Arduino Sketch Examples

This folder contains a collection of Arduino sketches showcasing various functionalities of the **PanamaHitek_Arduino** library.
These sketches are specifically designed to integrate with Java software, requiring certain
programs to run on the Arduino to enable serial communication between the microcontroller
and the Java application.

## Sketches Overview

### 1. [`rxtx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/rxtx_example.ino)

**Description:**
- Reads incoming data from the serial port.
- Converts the received data to a floating-point number.
- Computes the square root of the number.
- Sends the calculated result back via the serial port.

**Usage:**  
Use this sketch to test serial data transmission and reception in your Java software. It was meant to test
[rxtxExample.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxtxExample.java), 
which is a Swing application that sends a number to the Arduino, receives the square root of that number, and displays it a label.

The Swing GUI looks like this:

<p align="center">
  <img src="https://raw.githubusercontent.com/PanamaHitek/PanamaHitek_Arduino/refs/heads/master/src/main/resources/images/rxtxExample.png" alt="rxtxExample">
</p>
 
A number is entered in the text field, and the "Calcular" (Calculate) button sends the number to the Arduino. The square root of the number is displayed in the label below (Raíz Cuadrada, Square Root).

---

### 2. [`single_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/single_data_send.ino)

**Description:**  
This sketch sends a single integer value over serial communication. The integer is incremented with each transmission.

**Functions:**
- `setup()`: Initializes serial communication at a baud rate of 9600.
- `loop()`: Sends the value of variable `i` over the serial port and increments it every second.

**Usage:**  
Use this sketch to test serial data reception in your Java software by observing the incrementing values.
This sketch was meant to test the reception of a single data value with the [rxSimple.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxSimple.java)
running on the Java side.

---

### 3. [`tx_example.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/tx_example.ino)

**Description:**  
This sketch controls an LED connected to pin 13 of the Arduino based on serial commands. 
It listens for specific messages and responds accordingly.

**Functionality:**
- Reads incoming serial data.
- Turns the LED **on** when receiving the command `"on"`.
- Turns the LED **off** when receiving the command `"off"`.

**Usage:**  
To control the LED, send the appropriate commands ("on"/"off") via the serial monitor or a Java application.
This sketch was meant to test the transmission of data from the Java side using the 
[txExample.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/txExample.java) program.
This program renders a Swing GUI with two buttons to control the LED, as shown in the image below:

<p align="center">
  <img src="https://raw.githubusercontent.com/PanamaHitek/PanamaHitek_Arduino/refs/heads/master/src/main/resources/images/txExample.png" alt="rxtxExample">
</p>

The "Encender" (Turn On) button sends the command "on" to the Arduino, turning on the LED. The "Apagar" (Turn Off) button sends the command "off" to the Arduino, turning off the LED.

### 4. [`multiple_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/multiple_data_send.ino)

**Description:**
This sketch sends multiple data values over serial communication. The values are sent using the Serial.println() function,
which appends a newline character to each value. That way, the Java application can read the values line by line and parse them accordingly.

**Functionality:**
- Sends four values (integers) over serial communication.
- Values are initialized at 0, 10, 100 and 1000.
- The values are incremented by 1 with each transmission.

**Usage:**  
Use this sketch to test the reception of multiple data values in your Java software.
This sketch was meant to test the reception of multiple data values with the 
[rxMultiple.java](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino/rxMultiple.java)
program, which establishes serial communication with the Arduino and reads the values sent by the Arduino and displays them in the console.

### 5. [`double_data_send.ino`](https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/double_data_send.ino)

**Description:**
This sketch sends two integer values over serial communication. The values are sent using the Serial.println() function,
which appends a newline character to each value. That way, the Java application can read the values line by line and parse them accordingly.

**Functionality:**
- Sends two values (integers) over serial communication.
- Values are initialized at 0 and 10.
- The values are incremented by 1 with each transmission.

**Usage:** 
Use this sketch to test the reception of two data values in your Java software.




