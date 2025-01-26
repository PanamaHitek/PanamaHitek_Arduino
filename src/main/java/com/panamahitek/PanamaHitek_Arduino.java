/**
 * ======================= Aviso de Derechos de Autor =======================
 *
 * El presente código ha sido desarrollado por Antony García González en colaboración con el Equipo Creativo de Panama Hitek.
 *
 * Este código está protegido bajo la licencia GNU Lesser General Public License (LGPL) versión 2.1,
 * cuya copia puede consultarse en el siguiente enlace: http://www.gnu.org/licenses/lgpl.txt.
 *
 * Para su funcionamiento, el código utiliza la biblioteca JSSC (anteriormente conocida como RXTX),
 * la cual ha sido incorporada en su versión original, sin modificaciones por parte de nuestro equipo.
 * Expresamos nuestro agradecimiento a Alexey Sokolov, creador de JSSC, por proporcionar una herramienta
 * poderosa y eficiente que ha permitido mejorar nuestra biblioteca.
 *
 * Esta biblioteca es de código abierto y ha sido diseñada con el propósito de proporcionar a los usuarios,
 * desde principiantes hasta expertos, las herramientas necesarias para el desarrollo de sus proyectos
 * de manera sencilla e intuitiva.
 *
 * Se solicita que, en cualquier uso o distribución de este código, se reconozca su origen y autoría.
 * Este software fue desarrollado en la República de Panamá por Antony García González, Ingeniero Electromecánico,
 * docente e investigador de la Universidad de Panamá. Este repositorio ha sido mantenido desde el año 2013 hasta
 * la fecha.
 *
 * El autor es miembro del Equipo Creativo de Panama Hitek, una organización sin fines de lucro
 * dedicada a la enseñanza del desarrollo de software y hardware a través de su sitio web oficial: http://panamahitek.com.
 *
 * Apreciamos que esta compilación de código sea reconocida como un trabajo desarrollado por panameños,
 * con el propósito de contribuir tanto a Panamá como al resto del mundo.
 *
 * Para cualquier consulta o comunicación, puede escribirnos a: creativeteam@panamahitek.com.
 *
 * ========================================================================
 *
 * ======================= Copyright Notice =======================
 *
 * This code has been developed by Antony García González in collaboration with the Creative Team of Panama Hitek.
 *
 * This code is protected under the GNU Lesser General Public License (LGPL) version 2.1,
 * a copy of which can be found at the following link: http://www.gnu.org/licenses/lgpl.txt.
 *
 * This code makes use of the JSSC library (formerly known as RXTX),
 * which has been included in its original version, without modifications by our team.
 * We express our gratitude to Alexey Sokolov, creator of JSSC, for providing such a powerful
 * and efficient tool that has enabled improvements to our library.
 *
 * This library is open-source and has been designed to provide users, from beginners to experts,
 * with the necessary tools for the development of their projects in a simple and intuitive manner.
 *
 * We request that, in any use or distribution of this code, its origin and authorship be acknowledged.
 * This software was developed in the Republic of Panama by Antony García González, Electromechanical Engineer,
 * professor, and researcher at the University of Panama. This repository has been maintained from the year 2013
 * to the present.
 *
 * The author is a member of the Creative Team of Panama Hitek, a non-profit organization dedicated
 * to teaching software and hardware development through its official website: http://panamahitek.com.
 *
 * We appreciate that this compilation of code is recognized as a work developed by Panamanians,
 * with the purpose of contributing to Panama and the rest of the world.
 *
 * For any inquiries or communication, please contact us at: creativeteam@panamahitek.com.
 *
 * ========================================================================
 */

package com.panamahitek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jssc.*;
import static jssc.SerialPort.PURGE_RXCLEAR;
import static jssc.SerialPort.PURGE_TXCLEAR;

/**
 * @author Antony García González, de Proyecto Panama Hitek. Visita http://panamahitek.com
 */
public class PanamaHitek_Arduino {

    private SerialPort serialPort;
    private SerialPortEventListener events = null;
    private String connection = "";
    private String portName = "";
    private static int BYTESIZE = 8;
    private static int STOPBITS = 1;
    private static int PARITY = 0;
    private static int TIMEOUT = 2000;
    private String message = "";
    private boolean messageAvailable = false;
    private boolean availableInUse = false;

    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Constructor de la clase PanamaHitek_Arduino.
     *
     * Este constructor inicializa un objeto de la clase PanamaHitek_Arduino llamando al método `PanamaHitek()`,
     * el cual muestra los créditos e información relacionada con la librería.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Constructor for the PanamaHitek_Arduino class.
     *
     * This constructor initializes a PanamaHitek_Arduino object by calling the `PanamaHitek()` method,
     * which displays the credits and information about the library.
     */
    public PanamaHitek_Arduino() {
        PanamaHitek();
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Muestra los créditos de la librería PanamaHitek_Arduino.
     *
     * Este método imprime en la consola información sobre la versión de la librería,
     * el creador y los recursos relacionados con el proyecto Panama Hitek.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Displays the credits of the PanamaHitek_Arduino library.
     *
     * This method prints to the console information about the library version,
     * the creator, and related resources for the Panama Hitek project.
     */
    private void PanamaHitek() {
        System.out.println("PanamaHitek_Arduino Library, version 3.3.0");
        System.out.println("==============================================");
        System.out.println("Created by Antony Garcia Gonzalez");
        System.out.println("Electromechanical Engineer and creator of Project Panama Hitek");
        System.out.println("This library has been created from Java Simple Serial Connector (https://github.com/java-native/jssc)");
        System.out.println("You can find all the information about this AP at https://github.com/PanamaHitek/PanamaHitek_Arduino");
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Establece la paridad en la conexión con el puerto serie.
     *
     * Este método configura el tipo de paridad utilizado en la conexión serie.
     * La paridad es un mecanismo de detección de errores en la transmisión de datos.
     * Si se proporciona un valor fuera del rango permitido, se establecerá
     * la paridad por defecto: "Sin Paridad" (valor 0).
     *
     * @param input_Parity Valor entero que define el tipo de paridad a establecer:
     * <br>0 = Sin Paridad
     * <br>1 = Paridad Impar
     * <br>2 = Paridad Par
     * <br>3 = Paridad Marcada
     * <br>4 = Paridad Espaciada
     *
     * Si se ingresa un valor fuera de este rango, se establecerá el valor por defecto de "Sin Paridad".
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Sets the parity for the serial port connection.
     *
     * This method configures the type of parity used in the serial connection.
     * Parity is an error detection mechanism in data transmission.
     * If a value outside the allowed range is provided, the default
     * parity "No Parity" (value 0) will be set.
     *
     * @param input_Parity Integer value that defines the type of parity to set:
     * <br>0 = No Parity
     * <br>1 = Odd Parity
     * <br>2 = Even Parity
     * <br>3 = Mark Parity
     * <br>4 = Space Parity
     *
     * If an out-of-range value is provided, the default "No Parity" value will be set.
     *
     * @since v2.6.0
     */
    public void setParity(int input_Parity) {
        if ((input_Parity >= 0) && (input_Parity <= 4)) {
            PARITY = input_Parity;
        } else {
            PARITY = 0;
            System.out.println("La paridad solamente puede ser: \n"
                    + "0 = Sin Paridad\n"
                    + "1 = Paridad Impar\n"
                    + "2 = Paridad Par\n"
                    + "3 = Paridad Marcada\n"
                    + "4 = Paridad Espaciada\n"
                    + "Se conserva la paridad por defecto (0- Sin Paridad)");
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Establece el tamaño de byte (ByteSize) para la conexión serie.
     *
     * Este método configura el número de bits de datos por cada byte transmitido
     * a través del puerto serie. Se aceptan valores entre 5 y 8.
     *
     * @param Bytes Valor entero que define el tamaño de byte permitido (entre 5 y 8).
     *
     * Si se proporciona un valor fuera del rango permitido, se establecerá el valor
     * por defecto de 8 bytes y se mostrará un mensaje informativo en la consola.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Sets the byte size (ByteSize) for the serial connection.
     *
     * This method configures the number of data bits per transmitted byte
     * through the serial port. Accepted values range from 5 to 8.
     *
     * @param Bytes Integer value that defines the allowed byte size (between 5 and 8).
     *
     * If a value outside the allowed range is provided, the default value of
     * 8 bytes will be set, and an informative message will be displayed on the console.
     *
     * @since v2.6.0
     */
    public void setByteSize(int Bytes) {
        if ((Bytes >= 5) && (Bytes <= 8)) {
            BYTESIZE = Bytes;
        } else {
            BYTESIZE = 8;
            System.out.println("Sólo se aceptan valores entre 5 y 8 para el ByteSize "
                    + "\nSe conserva el valor por defecto (8 Bytes)");
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Establece la cantidad de StopBits para la conexión serie.
     *
     * Este método configura el número de StopBits utilizados en la conexión
     * del puerto serie. Los StopBits son necesarios para definir el final de
     * una transmisión de datos.
     *
     * @param Bits Valor entero que define la cantidad de StopBits:
     * <br>1 = 1 StopBit
     * <br>2 = 2 StopBits
     * <br>3 = 1.5 StopBits
     *
     * Si se proporciona un valor fuera del rango permitido (1-3),
     * se establecerá el valor por defecto de 1 StopBit y se mostrará
     * un mensaje informativo en la consola.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Sets the number of StopBits for the serial connection.
     *
     * This method configures the number of StopBits used in the serial
     * port connection. StopBits are required to indicate the end of a
     * data transmission.
     *
     * @param Bits Integer value defining the number of StopBits:
     * <br>1 = 1 StopBit
     * <br>2 = 2 StopBits
     * <br>3 = 1.5 StopBits
     *
     * If a value outside the allowed range (1-3) is provided,
     * the default value of 1 StopBit will be set, and an
     * informative message will be displayed on the console.
     *
     * @since v2.6.0
     */
    public void setStopBits(int Bits) {
        if ((Bits >= 1) && (Bits <= 3)) {
            STOPBITS = Bits;
        } else {
            STOPBITS = 1;
            System.out.println("Sólo se aceptan valores entre 1 y 3 para StopBit (3 es para 1.5 StopBits)."
                    + "\nSe conserva el valor por defecto (1 Bit)");
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Establece el tiempo de espera (timeout) para la conexión serie.
     *
     * Este método configura el valor del tiempo de espera para la conexión
     * con el puerto serie, definido en milisegundos. El tiempo de espera determina
     * cuánto tiempo la aplicación esperará por una respuesta antes de considerar
     * que la operación ha fallado.
     *
     * @param time Un valor entero que representa el tiempo de espera en milisegundos.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Sets the timeout value for the serial connection.
     *
     * This method configures the timeout value for the connection with the serial port,
     * defined in milliseconds. The timeout determines how long the application will wait
     * for a response before considering the operation as failed.
     *
     * @param time An integer value representing the timeout in milliseconds.
     *
     * @since v2.6.0
     */
    public void setTimeOut(int time) {
        this.TIMEOUT = time;
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Inicia la conexión con Arduino solo para la transmisión de datos a través del puerto serie.
     *
     * Este método establece una conexión con Arduino que permite la transmisión de información
     * desde la computadora hacia el dispositivo a través del puerto serie.
     *
     * @param PORT_NAME El nombre del puerto al que está conectado el Arduino.
     * @param DATA_RATE La velocidad de transmisión de datos en baudios por segundo.
     *
     * @throws ArduinoException Puede producirse uno de los siguientes errores:
     * <br>- Si se intenta iniciar la comunicación con Arduino más de una vez.
     * <br>- Si no se encuentra ningún dispositivo conectado al puerto serie.
     * <br>- Si el dispositivo conectado no es un Arduino.
     * <br>- Si el puerto seleccionado está siendo utilizado por otra aplicación.
     *
     * @since v1.0.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Initializes the connection with Arduino only for data transmission via the serial port.
     *
     * This method establishes a connection with Arduino, allowing the transmission of information
     * from the computer to the device through the serial port.
     *
     * @param PORT_NAME The name of the port to which the Arduino is connected.
     * @param DATA_RATE The data transmission speed in baud per second.
     *
     * @throws ArduinoException The following errors may occur:
     * <br>- If an attempt is made to start communication with Arduino more than once.
     * <br>- If no device is found connected to the serial port.
     * <br>- If the connected device is not an Arduino.
     * <br>- If the selected port is being used by another application.
     *
     * @since v1.0.0
     */
    public void arduinoTX(String PORT_NAME, int DATA_RATE) throws ArduinoException {
        if (connection.equals("")) {
            connection = "TX";
        } else {
            throw new ArduinoException(portName, "arduinoTX()", ArduinoException.TYPE_RXTX_EXCEPTION);
        }

        try {
            this.portName = PORT_NAME;
            serialPort = new SerialPort(PORT_NAME);
            serialPort.openPort();
            serialPort.setParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);
            flushSerialPort();
        } catch (SerialPortException ex) {
            throw new ArduinoException(portName, "arduinoTX()", ArduinoException.TYPE_PORT_NOT_OPENED);
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Inicia la conexión con Arduino para el envío y recepción de datos a través del puerto serie.
     *
     * Este método establece una conexión bidireccional con Arduino, permitiendo el envío y
     * recepción de información desde y hacia la computadora a través del puerto serie.
     *
     * @param PORT_NAME El nombre del puerto al que está conectado el Arduino.
     * @param DATA_RATE La velocidad de transmisión de datos en baudios por segundo.
     * @param events    Una instancia de la clase `SerialPortEventListener` para detectar
     *                  la recepción de información en el puerto serie.
     *
     * @throws ArduinoException Se pueden presentar las siguientes excepciones:
     * <br> - Si se intenta iniciar la comunicación con Arduino más de una vez.
     * <br> - Si no se encuentra ningún dispositivo conectado al puerto serie.
     * <br> - Si el dispositivo conectado no es un Arduino.
     * <br> - Si el puerto seleccionado está siendo utilizado por otra aplicación.
     *
     * @since v1.0.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Initializes the connection with Arduino for both sending and receiving data via the serial port.
     *
     * This method establishes a bidirectional connection with Arduino, enabling both data
     * transmission and reception between the computer and the device through the serial port.
     *
     * @param PORT_NAME The name of the port to which the Arduino is connected.
     * @param DATA_RATE The data transmission rate in baud per second.
     * @param events    An instance of the `SerialPortEventListener` class to detect
     *                  incoming information on the serial port.
     *
     * @throws ArduinoException The following exceptions may occur:
     * <br> - If an attempt is made to initiate communication with Arduino more than once.
     * <br> - If no device is found connected to the serial port.
     * <br> - If the connected device is not an Arduino.
     * <br> - If the selected port is being used by another application.
     *
     * @since v1.0.0
     */
    public void arduinoRXTX(String PORT_NAME, int DATA_RATE, SerialPortEventListener events) throws ArduinoException {
        if (connection.equals("")) {
            connection = "RXTX";
        } else {
            throw new ArduinoException(portName, "arduinoRXTX()", ArduinoException.TYPE_RXTX_EXCEPTION);
        }
        try {
            this.portName = PORT_NAME;
            serialPort = new SerialPort(PORT_NAME);
            serialPort.openPort();
            serialPort.setParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);
            serialPort.addEventListener(events);
            flushSerialPort();
        } catch (SerialPortException ex) {
            throw new ArduinoException(portName, "arduinoRXTX()", ArduinoException.TYPE_PORT_NOT_OPENED);
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Inicia la conexión con Arduino solo para la recepción de datos desde el puerto serie.
     *
     * Este método establece una conexión con Arduino para recibir información enviada
     * desde el dispositivo hacia la computadora a través del puerto serie.
     *
     * @param PORT_NAME El nombre del puerto al que está conectado el Arduino.
     * @param DATA_RATE La velocidad de transmisión de datos en baudios por segundo.
     * @param events    Una instancia de la clase `SerialPortEventListener` para detectar
     *                  la recepción de información en el puerto serie.
     *
     * @throws ArduinoException Se lanza una excepción si se intenta iniciar la comunicación
     *                          con Arduino más de una vez.
     *
     * @throws SerialPortException Se pueden presentar tres errores:
     * <br>- Si no se encuentra ningún dispositivo conectado al puerto serie.
     * <br>- Si el dispositivo conectado no es un Arduino.
     * <br>- Si el puerto seleccionado está siendo utilizado por otra aplicación.
     *
     * @since v1.0.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Initiates the connection with Arduino only for data reception via the serial port.
     *
     * This method establishes a connection with Arduino to receive information sent
     * from the device to the computer through the serial port.
     *
     * @param PORT_NAME The name of the port to which the Arduino is connected.
     * @param DATA_RATE The data transmission rate in baud per second.
     * @param events    An instance of the `SerialPortEventListener` class to detect
     *                  when information is received on the serial port.
     *
     * @throws ArduinoException An exception is thrown if an attempt is made to start
     *                          communication with Arduino more than once.
     *
     * @throws SerialPortException Three types of errors may occur:
     * <br>- If no device is found connected to the serial port.
     * <br>- If the connected device is not an Arduino.
     * <br>- If the selected port is being used by another application.
     *
     * @since v1.0.0
     */
    public void arduinoRX(String PORT_NAME, int DATA_RATE, SerialPortEventListener events) throws ArduinoException, SerialPortException {

        if (connection.equals("")) {
            connection = "RX";
        } else {
            throw new ArduinoException(portName, "arduinoRX()", ArduinoException.TYPE_RXTX_EXCEPTION);
        }
        try {
            this.portName = PORT_NAME;
            serialPort = new SerialPort(PORT_NAME);
            serialPort.openPort();
            serialPort.setParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);
            serialPort.addEventListener(events);
            flushSerialPort();
        } catch (SerialPortException ex) {
            throw new ArduinoException(portName, "arduinoRX()", ArduinoException.TYPE_PORT_NOT_OPENED);
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Envía una cadena de texto desde Java hacia Arduino a través del puerto serie.
     *
     * Este método permite enviar datos en forma de cadena de caracteres (`String`)
     * desde la aplicación Java hacia un dispositivo Arduino a través del puerto serie.
     *
     * @param data Una cadena de caracteres (`String`) que contiene los datos a enviar.
     *
     * @throws ArduinoException Se lanza una excepción si la conexión se ha establecido
     *                          en modo recepción utilizando `ArduinoRX()`, ya que en
     *                          este modo no se permite el envío de datos.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha establecido
     *                             una conexión con Arduino.
     *
     * @since v1.0.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Sends a string of text from Java to Arduino via the serial port.
     *
     * This method allows sending data in the form of a string (`String`) from
     * the Java application to an Arduino device through the serial port.
     *
     * @param data A string containing the data to be sent.
     *
     * @throws ArduinoException An exception is thrown if the connection has been
     *                          established in receive mode using `ArduinoRX()`,
     *                          as data transmission is not allowed in this mode.
     *
     * @throws SerialPortException An exception is thrown if the connection
     *                             with Arduino has not been established.
     *
     * @since v1.0.0
     */
    public void sendData(String data) throws ArduinoException, SerialPortException {
        if (connection.equals("RX")) {
            throw new ArduinoException(portName, "sendData()", ArduinoException.TYPE_WRONG_SEND_DATA_CONNECTION);
        } else if (connection.equals("")) {
            throw new ArduinoException(portName, "sendData()", ArduinoException.TYPE_SEND_DATA);
        } else if (connection.equals("TX") || connection.equals("RXTX")) {
            serialPort.writeBytes(data.getBytes());
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Envía un byte de datos desde Java hacia Arduino a través del puerto serie.
     *
     * Este método envía un valor en forma de byte (entre 0 y 255) a Arduino.
     * A diferencia del método `sendData(String data)`, donde la información se
     * envía como una cadena de caracteres (`String`), en este caso se envía
     * directamente como un byte.
     *
     * @param data El byte que se desea enviar (un valor entre 0 y 255).
     *
     * @throws ArduinoException Se lanza una excepción si la conexión ha sido
     *                          establecida en modo recepción (`ArduinoRX()`),
     *                          ya que en este modo no se permite el envío de datos.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha iniciado
     *                             una conexión con Arduino.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Sends a byte of data from Java to Arduino via the serial port.
     *
     * This method sends a value as a byte (between 0 and 255) to Arduino.
     * Unlike the `sendData(String data)` method, where the information is sent
     * as a string, in this case, it is sent directly as a byte.
     *
     * @param data The byte to be sent (a value between 0 and 255).
     *
     * @throws ArduinoException An exception is thrown if the connection was
     *                          established in receive mode (`ArduinoRX()`),
     *                          as data transmission is not allowed in this mode.
     *
     * @throws SerialPortException An exception is thrown if the connection
     *                             with Arduino has not been established.
     *
     * @since v2.6.0
     */
    public void sendByte(int data) throws ArduinoException, SerialPortException {

        if (connection.equals("RX")) {
            throw new ArduinoException(portName, "sendByte()", ArduinoException.TYPE_SEND_DATA);
        } else if (connection.equals("")) {
            throw new ArduinoException(portName, "sendByte()", ArduinoException.TYPE_NO_ARDUINO_CONNECTION);
        } else if (connection.equals("TX") || connection.equals("RXTX")) {
            serialPort.writeByte((byte) data);
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Recibe datos enviados desde Arduino a través del puerto serie.
     *
     * Este método recibe los datos exactamente como son enviados desde Arduino,
     * caracter por caracter, y los devuelve en un arreglo de bytes.
     *
     * @return Un arreglo de bytes con los datos recibidos desde el puerto serie.
     *
     * @throws ArduinoException Se lanza una excepción si la conexión ha sido iniciada
     *                          utilizando el método `ArduinoTX()`, ya que este método
     *                          no permite la recepción de datos.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha establecido
     *                             una conexión con Arduino.
     *
     * @since v2.8.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Receives data sent from Arduino via the serial port.
     *
     * This method receives data exactly as it is sent from Arduino,
     * character by character, and returns it as a byte array.
     *
     * @return A byte array containing the data received from the serial port.
     *
     * @throws ArduinoException An exception is thrown if the connection was initiated
     *                          using the `ArduinoTX()` method, as this method does not
     *                          allow data reception.
     *
     * @throws SerialPortException An exception is thrown if the connection
     *                             with Arduino has not been established.
     *
     * @since v2.8.0
     */
    public byte[] receiveData() throws ArduinoException, SerialPortException {
        if (connection.equals("TX")) {
            throw new ArduinoException(portName, "receiveData()", ArduinoException.TYPE_RECEIVE_DATA);
        } else if (connection.equals("")) {
            throw new ArduinoException(portName, "receiveData()", ArduinoException.TYPE_NO_ARDUINO_CONNECTION);
        } else {
            return serialPort.readBytes();
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Verifica la disponibilidad de un mensaje recibido desde Arduino.
     *
     * Este método devuelve un valor `TRUE` cuando se detecta un salto de línea (`\n`)
     * en la transmisión de datos desde Arduino hacia la computadora. La separación entre
     * mensajes depende del uso del método `Serial.println()` en el código de Arduino,
     * ya que esta función busca saltos de línea para identificar mensajes completos.
     * Si se utiliza `Serial.print()`, la librería no reconocerá los mensajes enviados.
     *
     * @return Un valor booleano que será `TRUE` cuando se reciba un salto de línea
     *         en la transmisión de datos desde Arduino; de lo contrario, devuelve `FALSE`.
     *
     * @throws ArduinoException Se lanza una excepción si la conexión con Arduino
     *                          no ha sido iniciada o si se ha establecido utilizando
     *                          el método `ArduinoTX()`, el cual no permite recibir datos.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha iniciado
     *                             una conexión con Arduino.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Checks for the availability of a received message from Arduino.
     *
     * This method returns `TRUE` when a newline character (`\n`) is detected in the
     * data transmission from Arduino to the computer. The separation between messages
     * depends on the use of the `Serial.println()` method in the Arduino code, as this
     * function searches for newline characters to identify complete messages.
     * If `Serial.print()` is used, the library will not recognize the sent messages.
     *
     * @return A boolean value that will be `TRUE` when a newline character is received
     *         in the data transmission from Arduino; otherwise, it returns `FALSE`.
     *
     * @throws ArduinoException An exception is thrown if the connection with Arduino
     *                          has not been established or if it was initiated using
     *                          the `ArduinoTX()` method, which does not allow data reception.
     *
     * @throws SerialPortException An exception is thrown if the connection with Arduino
     *                             has not been established.
     *
     * @since v2.6.0
     */
    public boolean isMessageAvailable() throws SerialPortException, ArduinoException {
        availableInUse = true;
        serialRead();
        return messageAvailable;
    }


/**
 * ===================================================
 * Documentación en Español
 * ===================================================
 *
 * Realiza la lectura de datos enviados desde Arduino mediante `Serial.println()`.
 *
 * Este método recibe y procesa los datos enviados desde Arduino. Detecta la presencia
 * de un salto de línea (`\n`), momento en el cual se considera que el mensaje está completo
 * y se marca como disponible para su posterior procesamiento.
 *
 * @throws ArduinoException Se lanza una excepción si la conexión con Arduino
 *                          no ha sido iniciada o si la conexión se ha establecido
 *                          utilizando el método `ArduinoTX()`, el cual no permite
 *                          recibir datos.
 *
 * @throws SerialPortException Se lanza una excepción si no se ha iniciado
 *                             una conexión con Arduino.
 *
 * ===================================================
 * Documentation in English
 * ===================================================
 *
 * Reads data sent from Arduino using `Serial.println()`.
 *
 * This method receives and processes the data sent from Arduino. It detects the presence
 * of a newline character (`\n`), at which point the message is considered complete
 * and marked as available for further processing.
 *
 * @throws ArduinoException An exception is thrown if the connection with Arduino
 *                          has not been established or if the connection was initiated
 *                          using the `ArduinoTX()` method, which does not allow receiving data.
 *
 * @throws SerialPortException An exception is thrown if the connection with Arduino
 *                             has not been established.
 */
    private void serialRead() throws ArduinoException, SerialPortException {
        int inputByte;
        byte[] buffer = receiveData();
        if (buffer != null) {
            int bufferLength = buffer.length;
            if (!messageAvailable) {
                for (int i = 0; i < bufferLength; i++) {
                    inputByte = buffer[i];
                    if (inputByte > 0) {
                        if (inputByte != 13) { // Ignorar retorno de carro (carriage return)
                            if (inputByte != 10) { // Detectar salto de línea (newline)
                                message = message + (char) inputByte;
                            } else {
                                messageAvailable = true;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Imprime el mensaje almacenado en el buffer del puerto serie.
     *
     * Este método debe utilizarse dentro de una estructura condicional. Cuando el
     * método `isMessageAvailable()` devuelve un valor `true`, este método obtiene
     * e imprime el mensaje almacenado en el buffer del puerto serie.
     *
     * @return Una cadena de texto (`String`) con el mensaje recibido. Si no hay
     *         datos disponibles, se devuelve el mensaje predeterminado:
     *         "No hay datos disponibles".
     *
     * @throws ArduinoException Se lanza una excepción si la conexión con Arduino
     *                          no ha sido iniciada o si la conexión se ha establecido
     *                          utilizando el método `ArduinoTX()`, que no permite
     *                          recibir datos.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha iniciado
     *                             una conexión con Arduino.
     *
     * @since v2.0.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Prints the message stored in the serial port buffer.
     *
     * This method should be used within a conditional structure. When the method
     * `isMessageAvailable()` returns a `true` value, this method retrieves and prints
     * the message stored in the serial port buffer.
     *
     * @return A string containing the received message. If no data is available,
     *         the default message "No hay datos disponibles" is returned.
     *
     * @throws ArduinoException An exception is thrown if the connection with Arduino
     *                          has not been established or if the connection was
     *                          initiated using the `ArduinoTX()` method, which does
     *                          not allow receiving data.
     *
     * @throws SerialPortException An exception is thrown if the connection with Arduino
     *                             has not been established.
     *
     * @since v2.0.0
     */
    public String printMessage() throws SerialPortException, ArduinoException {
        String output = "No hay datos disponibles";
        if (!availableInUse) {
            serialRead();
        }
        if (isMessageAvailable()) {
            output = message;
            message = "";
            messageAvailable = false;
        }
        return output;
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Devuelve la cantidad de puertos serie activos en el sistema.
     *
     * Este método determina y devuelve el número de puertos serie activos
     * detectados en la computadora. La cantidad de puertos disponibles
     * dependerá de los dispositivos conectados a través del puerto serie.
     *
     * @return Un número entero positivo mayor o igual a cero que indica
     *         la cantidad de puertos serie disponibles.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Returns the number of active serial ports in the system.
     *
     * This method determines and returns the number of active serial ports
     * detected on the computer. The number of available ports depends on
     * the devices connected through the serial port.
     *
     * @return A positive integer greater than or equal to zero indicating
     *         the number of available serial ports.
     *
     * @since v2.6.0
     */
    public int getPortsAvailable() {
        return SerialPortList.getPortNames().length;
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Obtiene una lista de los puertos serie disponibles en el sistema.
     *
     * Este método recupera y devuelve una lista con los nombres de todos los puertos
     * serie disponibles en el sistema, que pueden ser utilizados para la comunicación.
     *
     * @return Una lista de cadenas de texto con los nombres de los puertos serie disponibles.
     *
     * @since v2.6.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Retrieves a list of available serial ports in the system.
     *
     * This method fetches and returns a list containing the names of all available
     * serial ports in the system, which can be used for communication.
     *
     * @return A list of strings containing the names of the available serial ports.
     *
     * @since v2.6.0
     */
    public List<String> getSerialPorts() {
        List<String> ports = new ArrayList<>();
        String[] portNames = SerialPortList.getPortNames();
        ports.addAll(Arrays.asList(portNames));
        return ports;
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Finaliza la conexión con Arduino en el puerto serie.
     *
     * Este método cierra la conexión con Arduino establecida en el puerto serie.
     * Si no se ha iniciado una conexión previamente, se lanzará una excepción.
     *
     * @throws ArduinoException Se lanza una excepción si se intenta cerrar
     *                          la conexión sin haberla iniciado previamente.
     *
     * @since v2.0.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Terminates the connection with Arduino on the serial port.
     *
     * This method closes the established connection with Arduino on the serial port.
     * If no connection has been previously initiated, an exception will be thrown.
     *
     * @throws ArduinoException An exception is thrown if an attempt is made to
     *                          close the connection without it being previously initiated.
     *
     * @since v2.0.0
     */
    public void killArduinoConnection() throws ArduinoException {
        if (connection.equals("")) {
            throw new ArduinoException(this.portName, "killArduinoConnection()", ArduinoException.TYPE_KILL_ARDUINO_CONNECTION);
        } else {
            try {
                serialPort.closePort();
            } catch (SerialPortException ex) {
                throw new ArduinoException(portName, "killArduinoConnection()", ArduinoException.TYPE_CLOSE_PORT);
            }
            connection = "";
            System.out.println("Conexión con Arduino finalizada");
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Limpia los buffers de entrada y salida del puerto serie.
     *
     * Este método elimina los datos almacenados en los buffers de entrada y
     * salida del puerto serie, garantizando que no haya datos residuales
     * en futuras transmisiones.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha
     *                            establecido una conexión con el puerto serie.
     *
     * @since 2.8.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Clears the input and output buffers of the serial port.
     *
     * This method removes any data stored in the serial port's input
     * and output buffers, ensuring that no residual data remains for
     * future transmissions.
     *
     * @throws SerialPortException An exception is thrown if a connection
     *                            to the serial port has not been established.
     *
     * @since 2.8.0
     */
    public void flushSerialPort() throws SerialPortException {
        serialPort.purgePort(PURGE_RXCLEAR);
        serialPort.purgePort(PURGE_TXCLEAR);
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Obtiene la cantidad de bytes disponibles para ser leídos en el buffer de entrada del puerto serie.
     *
     * @return La cantidad de bytes disponibles en el buffer de entrada del puerto serie.
     *
     * @throws SerialPortException Se lanza una excepción si no se ha establecido
     *                            una conexión con el puerto serie.
     *
     * @since 2.8.0
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Retrieves the number of bytes available to be read from the serial port's input buffer.
     *
     * @return The number of bytes available in the serial port's input buffer.
     *
     * @throws SerialPortException An exception is thrown if a connection to
     *                            the serial port has not been established.
     *
     * @since 2.8.0
     */
    public int getInputBytesAvailable() throws SerialPortException {
        return serialPort.getInputBufferBytesCount();
    }


    /**
     * ===============================================
     * Documentación en Español
     * ===============================================
     *
     * Obtiene el event listener asociado al puerto serial.
     *
     * Este método devuelve el event listener que se agrega al puerto serial (`serialPort`)
     * cuando se implementan los métodos `arduinoRX()` y `arduinoRXTX()`.
     *
     * @return El event listener registrado para el puerto serial.
     *
     * @throws ArduinoException Se lanza una excepción si se invoca este método
     *                          sin haber agregado previamente un `EventListener`.
     *
     * @since 2.8.0
     *
     * ===============================================
     * Documentation in English
     * ===============================================
     *
     * Retrieves the event listener associated with the serial port.
     *
     * This method returns the event listener added to the serial port (`serialPort`)
     * when the methods `arduinoRX()` and `arduinoRXTX()` are implemented.
     *
     * @return The event listener registered for the serial port.
     *
     * @throws ArduinoException An exception is thrown if this method is called
     *                          without having previously added an `EventListener`.
     *
     * @since 2.8.0
     */
    public SerialPortEventListener getEventListener() throws ArduinoException {
        if (events != null) {
            return events;
        } else {
            throw new ArduinoException(portName, "getEventListener()", ArduinoException.TYPE_NO_EVENT_LISTENER);
        }
    }

}
