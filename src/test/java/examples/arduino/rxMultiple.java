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
package examples.arduino;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * ============ Documentación en Español =====================
 *
 * Clase que permite la recepción simultánea de múltiples datos enviados 
 * por un dispositivo Arduino a través del puerto serie.
 * 
 * Este programa debe utilizarse en conjunto con el código de Arduino 
 * `multiple_data_send.ino`, el cual envía datos de múltiples sensores 
 * separados por comas (`,`) y finalizados con un salto de línea (`\n`). 
 * 
 * El código de Arduino genera datos simulados de sensores que representan 
 * diferentes lecturas, como temperatura, humedad, luz, entre otros. 
 * La comunicación se realiza a una velocidad de 9600 baudios.
 * 
 * Funcionamiento:
 * 1. Se establece la conexión con el puerto serie correspondiente.
 * 2. Los datos recibidos se almacenan y se imprimen en la consola en el 
 *    mismo orden en que fueron enviados.
 * 3. Se limpia el buffer de recepción para permitir nuevos datos.
 * 
 * Código Arduino disponible en:
 * https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/multiple_data_send.ino
 *
 * ============ Documentation in English =====================
 *
 * This class enables the simultaneous reception of multiple data streams 
 * sent by an Arduino device via the serial port.
 * 
 * It should be used in conjunction with the Arduino code 
 * `multiple_data_send.ino`, which sends data from multiple sensors 
 * separated by commas (`,`) and terminated with a newline (`\n`). 
 * 
 * The Arduino code generates simulated sensor data representing 
 * different readings, such as temperature, humidity, and light levels. 
 * Communication is set at a baud rate of 9600.
 * 
 * Workflow:
 * 1. Establishes the connection with the corresponding serial port.
 * 2. Received data is stored and printed in the same order as sent.
 * 3. The reception buffer is cleared to allow new data.
 * 
 * Arduino code available at:
 * https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/multiple_data_send.ino
 *
 * @author Antony Garcia Gonzalez
 */
public class rxMultiple {


    // ============ Documentación en Español =====================
    //
    // Se crea una instancia de la clase PanamaHitek_Arduino para gestionar la comunicación
    // serie con el Arduino.
    //
    // ============ Documentation in English =====================
    //
    // An instance of the PanamaHitek_Arduino class is created to manage serial 
    // communication with the Arduino.
    static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();

    /**
     * ============ Documentación en Español =====================
     *
     * Se crea una instancia de la clase PanamaHitek_MultiMessage para gestionar la 
     * recepción de múltiples datos simultáneos desde el Arduino. En este caso, se 
     * configura para recibir datos de 4 sensores.
     *
     * ============ Documentation in English =====================
     *
     * An instance of the PanamaHitek_MultiMessage class is created to handle the reception
     * of multiple simultaneous data streams from the Arduino. In this case, it is set 
     * to receive data from 4 sensors.
     */
    static PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(4, ino);

    /**
     * ============ Documentación en Español =====================
     *
     * Se crea un escuchador de eventos para el puerto serie. Este escuchador se activa 
     * cuando se reciben datos del Arduino y los procesa en el método `serialEvent`.
     *
     * ============ Documentation in English =====================
     *
     * A serial port event listener is created. This listener is triggered when data 
     * is received from the Arduino and processed in the `serialEvent` method.
     */
    static SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent serialPortEvent) {
            try {
                /*
                ============ Documentación en Español =====================
                Los datos en el puerto serie se envían carácter por carácter. Para esperar 
                la recepción completa de un mensaje antes de imprimirlo, el método 
                `dataReceptionCompleted()` devolverá TRUE una vez que se haya recibido todo 
                el mensaje. Luego, se puede imprimir usando `printMessage()`.
                
                ============ Documentation in English =====================
                Data on the serial port is sent character by character. To wait for the 
                complete reception of a message before printing it, the method 
                `dataReceptionCompleted()` will return TRUE once the message is fully 
                received. It can then be printed using `printMessage()`.
                */
                if (multi.dataReceptionCompleted()) {
                    /**
                     * ============ Documentación en Español =====================
                     * Se imprimen los datos recibidos en el mismo orden en que se enviaron
                     * desde el Arduino. Los índices 0, 1, 2 y 3 corresponden a cada sensor.
                     *
                     * ============ Documentation in English =====================
                     * The received data is printed in the same order it was sent 
                     * from the Arduino. Indices 0, 1, 2, and 3 correspond to each sensor.
                     */
                    System.out.println("Sensor 1: " + multi.getMessage(0));
                    System.out.println("Sensor 2: " + multi.getMessage(1));
                    System.out.println("Sensor 3: " + multi.getMessage(2));
                    System.out.println("Sensor 4: " + multi.getMessage(3));
                    System.out.println("_______________________________"); // Separator

                    /*
                    ============ Documentación en Español =====================
                    Una vez que los datos han sido impresos, se llama al método `flushBuffer()`
                    para limpiar el buffer y permitir la recepción del siguiente conjunto de datos.
                    
                    ============ Documentation in English =====================
                    Once the data has been printed, the `flushBuffer()` method is called 
                    to clear the buffer and allow the reception of the next data set.
                    */
                    multi.flushBuffer();
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, "Error en el puerto serie", ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, "Error de comunicación con Arduino", ex);
            }
        }
    };


    /**
     * ============ Documentación en Español =====================
     *
     * Método principal que inicia la comunicación con el Arduino a través del puerto serie.
     * 
     * Se establece la conexión con un puerto serie específico (por ejemplo, "COM5") a una velocidad 
     * de transmisión de 9600 baudios, y se configura un escuchador de eventos para recibir datos 
     * desde el Arduino. El puerto utilizado puede variar según el sistema operativo y la 
     * configuración del usuario.
     * 
     * En caso de errores durante la conexión o transmisión de datos, se capturan y registran 
     * las excepciones correspondientes.
     * 
     * @param args Argumentos de línea de comandos (no utilizados en este programa).
     *
     * ============ Documentation in English =====================
     *
     * Main method that initializes communication with the Arduino through the serial port.
     * 
     * A connection is established to a specific serial port (e.g., "COM5") at a baud rate of 9600, 
     * and an event listener is set up to receive data from the Arduino. The port used may vary 
     * depending on the operating system and user configuration.
     * 
     * If any errors occur during connection or data transmission, the corresponding exceptions 
     * are caught and logged.
     * 
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        try {
            // Inicia la recepción de datos desde un puerto serie específico (ajustable) a 9600 baudios
            ino.arduinoRX("COM5", 9600, listener);
        } catch (ArduinoException ex) {
            // Registra errores relacionados con la comunicación con Arduino
            Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, "Error en la comunicación con Arduino", ex);
        } catch (SerialPortException ex) {
            // Registra errores r

}}}
