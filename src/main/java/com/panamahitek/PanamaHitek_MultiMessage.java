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
import java.util.List;
import jssc.SerialPortException;

/**
 * @author Antony García González, de Proyecto Panama Hitek. Visita http://panamahitek.com
 */
public class PanamaHitek_MultiMessage {

    // Variables
    private static int inputMesages = 0;
    private static List<String> inputBuffer;
    private final PanamaHitek_Arduino arduinoObject;

    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Clase diseñada para la lectura de múltiples datos desde Arduino.
     *
     * Esta clase permite recibir múltiples datos simultáneamente desde Arduino,
     * como lecturas de diferentes sensores, sin necesidad de implementar complejas
     * secuencias lógicas para diferenciarlas.
     *
     * @param inputMessages Número de mensajes simultáneos que se espera recibir.
     *                      Por ejemplo, si se desea recibir humedad y temperatura
     *                      al mismo tiempo, el valor de este parámetro debe ser 2.
     * @param arduinoObject Un objeto de la clase `PanamaHitek_Arduino` que ha
     *                      sido previamente inicializado para la comunicación con Arduino.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Class designed for reading multiple data inputs from Arduino.
     *
     * This class allows receiving multiple simultaneous data readings from Arduino,
     * such as readings from different sensors, without the need to implement complex
     * logical sequences to distinguish between them.
     *
     * @param inputMessages The number of simultaneous messages expected to be received.
     *                      For example, if humidity and temperature are to be received
     *                      simultaneously, the value of this parameter should be 2.
     * @param arduinoObject An instance of the `PanamaHitek_Arduino` class that
     *                      has been previously initialized for communication with Arduino.
     */
    public PanamaHitek_MultiMessage(int inputMessages, PanamaHitek_Arduino arduinoObject) {
        this.arduinoObject = arduinoObject;
        inputMesages = inputMessages;
        inputBuffer = new ArrayList<>();
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Verifica si se ha completado la recepción de datos desde el puerto serie.
     *
     * Este método revisa continuamente si se ha recibido la cantidad de mensajes
     * establecida al crear el objeto de la clase `PanamaHitek_MultiMessage`.
     * Los datos se procesan caracter por caracter, y cuando se detecta la secuencia
     * de terminación (`1310`), se considera un mensaje completo.
     *
     * @return `true` si se ha completado la recepción de datos, `false` si aún no se ha finalizado la lectura.
     *
     * @throws ArduinoException Se lanza una excepción si hay un problema en la comunicación con el dispositivo Arduino.
     * @throws SerialPortException Se lanza una excepción si ocurre un error en la comunicación con el puerto serie.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Checks if data reception from the serial port is complete.
     *
     * This method continuously checks if the specified number of messages
     * has been received when creating the `PanamaHitek_MultiMessage` object.
     * The data is processed character by character, and when the termination
     * sequence (`1310`) is detected, a complete message is considered received.
     *
     * @return `true` if data reception is complete, `false` if reading is not yet finished.
     *
     * @throws ArduinoException Thrown if there is an issue with the communication with the Arduino device.
     * @throws SerialPortException Thrown if an error occurs in communication with the serial port.
     */
    public boolean dataReceptionCompleted() throws ArduinoException, SerialPortException {
        String str = "";
        int i = 0;

        if (arduinoObject.getInputBytesAvailable() > 0) {
            while (i < inputMesages) {
                if (arduinoObject.getInputBytesAvailable() > 0) {
                    byte[] buffer = arduinoObject.receiveData();
                    int bufferLenth = buffer.length;
                    for (int j = 0; j < bufferLenth; j++) {
                        int n = buffer[j];
                        if (n != 10 && n != 13) {
                            str += (char) n;
                        } else {
                            str += n;
                            if (str.contains("1310")) {
                                inputBuffer.add(str.replaceAll("1310", ""));
                                i++;
                                str = "";
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Obtiene la información almacenada en el búfer de entrada.
     *
     * Este método permite obtener un mensaje específico leído desde el puerto serie,
     * utilizando un índice que corresponde al orden en que los datos fueron enviados
     * desde Arduino mediante `Serial.println()`.
     *
     * @param index Un valor entero que indica la posición del mensaje en el búfer.
     *              La posición se define por el orden en que los datos se imprimieron
     *              en el `Serial.println()` de Arduino.
     *
     * @return Una cadena de caracteres (`String`) con la información solicitada.
     *
     * @throws IndexOutOfBoundsException Se lanza una excepción si el índice proporcionado
     *                                   está fuera del rango del búfer de entrada.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Retrieves the information stored in the input buffer.
     *
     * This method allows retrieving a specific message read from the serial port,
     * using an index that corresponds to the order in which the data was sent
     * from Arduino using `Serial.println()`.
     *
     * @param index An integer value indicating the position of the message in the buffer.
     *              The position is determined by the order in which the data was printed
     *              in Arduino's `Serial.println()`.
     *
     * @return A string containing the requested information.
     *
     * @throws IndexOutOfBoundsException An exception is thrown if the provided index
     *                                   is out of range for the input buffer.
     */
    public String getMessage(int index) {
        String Output = inputBuffer.get(index);
        return Output;
    }


    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Devuelve una lista con los mensajes recibidos en la última lectura.
     *
     * Este método proporciona una lista que contiene todos los mensajes recibidos
     * durante la última sesión de lectura desde el puerto serie.
     *
     * @return Una lista de cadenas de texto (`List<String>`) con los mensajes recibidos.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Returns a list of messages received in the last reading.
     *
     * This method provides a list containing all messages received
     * during the last reading session from the serial port.
     *
     * @return A list of strings (`List<String>`) containing the received messages.
     */
    public List<String> getMessageList() {
        return inputBuffer;
    }

    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Limpia el búfer de entrada y restablece las variables para una nueva lectura.
     *
     * Este método elimina todos los mensajes almacenados en el búfer de entrada
     * y restablece las variables necesarias, preparando la instancia para recibir
     * nuevos datos desde el puerto serie.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Clears the input buffer and resets variables for a new reading.
     *
     * This method removes all messages stored in the input buffer
     * and resets necessary variables, preparing the instance to receive
     * new data from the serial port.
     */
    public void flushBuffer() {
        inputBuffer.clear();
    }
}
