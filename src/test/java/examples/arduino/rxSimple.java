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
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * ============ Documentación en Español =====================
 *
 * Clase que permite la recepción de datos de un dispositivo Arduino a través del puerto serie.
 * 
 * Este programa debe utilizarse junto con el código de Arduino 
 * `single_data_send.ino`, disponible en el repositorio de PanamaHitek. 
 * Dicho código envía datos como cadenas de texto terminadas con un salto 
 * de línea (`\n`), generando valores numéricos de forma continua.
 * 
 * La comunicación se realiza a través del puerto serie configurado a una velocidad de 
 * transmisión de 115200 baudios.
 * 
 * Funcionamiento:
 * 1. Se establece la conexión con el puerto serie especificado (por ejemplo, "COM22").
 * 2. Cuando se recibe un mensaje completo, se imprime en la consola.
 * 3. Se capturan y registran errores relacionados con la conexión al puerto serie o la 
 *    comunicación con el Arduino.
 * 
 * Código Arduino disponible en:
 * https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/single_data_send.ino
 *
 * ============ Documentation in English =====================
 *
 * This class enables data reception from an Arduino device via the serial port.
 * 
 * It should be used together with the Arduino code `single_data_send.ino`, 
 * available in the PanamaHitek repository. This script sends data as text strings 
 * terminated with a newline (`\n`), continuously generating numerical values.
 * 
 * Communication is conducted through the serial port configured at a baud rate of 115200.
 * 
 * Workflow:
 * 1. Establishes a connection with the specified serial port (e.g., "COM22").
 * 2. When a complete message is received, it is printed to the console.
 * 3. Errors related to the serial port connection or Arduino communication are caught and logged.
 * 
 * Arduino code available at:
 * https://github.com/PanamaHitek/PanamaHitek_Arduino/blob/master/src/test/java/examples/arduino_sketches/single_data_send.ino
 *
 * @author Antony Garcia Gonzalez
 */
public class rxSimple {
    
     static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
     
      /**
     * ============ Documentación en Español =====================
     *
     * Se crea un "escuchador de eventos" para el puerto serie. Este escuchador se activa 
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
                `isMessageAvailable()` devolverá TRUE cuando el mensaje esté disponible.
                
                ============ Documentation in English =====================
                Data on the serial port is sent character by character. To wait for the 
                complete reception of a message before printing it, the `isMessageAvailable()` 
                method will return TRUE when the message is available.
                */
                if (ino.isMessageAvailable()) {
                    // Se le asigna el mensaje recibido a la variable msg
                    String msg = ino.printMessage();
                    // Se imprime el mensaje recibido
                    System.out.println("Mensaje recibido --> " + msg);
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, "Error en el puerto serie", ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, "Error en la comunicación con Arduino", ex);
            }
        }
    };
    
   /**
     * ============ Documentación en Español =====================
     *
     * Método principal que inicia la comunicación con el Arduino a través del puerto serie.
     * 
     * Se establece la conexión con el puerto COM22 a una velocidad de transmisión de 115200 baudios,
     * y configura un escuchador de eventos para recibir datos desde el Arduino.
     * 
     * ============ Documentation in English =====================
     *
     * Main method that initializes communication with the Arduino through the serial port.
     * 
     * A connection is established to port COM22 at a baud rate of 115200, and an event 
     * listener is set up to receive data from the Arduino.
     */
    public static void main(String[] args) {
        try {
            ino.arduinoRX("COM22", 115200, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, "Error en la comunicación con Arduino", ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, "Error en la conexión del puerto serie", ex);
        }
    }
     
}
