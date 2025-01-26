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

/**
 * [ES] <br>
 * La clase `ArduinoException` maneja las excepciones específicas que pueden ocurrir durante la comunicación con un dispositivo Arduino. <br>
 * Proporciona mensajes de error detallados en inglés y español, dependiendo del idioma seleccionado. Para más información, visite https://panamahitek.com. <br>
 * <br>
 * [EN] <br>
 * The `ArduinoException` class handles specific exceptions that may occur during communication with an Arduino device. <br>
 * It provides detailed error messages in both English and Spanish, depending on the selected language. For more information, visit https://panamahitek.com. <br>
 * <br>
 *
 * @author Antony García González
 */

public class ArduinoException extends Exception {

    private static String portName;
    private static String methodName;
    private static String exceptionType;
    private static boolean isEnglish = false; // Default language: Spanish (false)

    // Error messages in English (default variable names)
    final public static String TYPE_PORT_ALREADY_OPENED = "The port you are trying to open is being used by another device";
    final public static String TYPE_PORT_NOT_OPENED = "Port not opened";
    final public static String TYPE_RXTX_EXCEPTION = "Cannot start the connection with Arduino twice";
    final public static String TYPE_NO_ARDUINO_AT_PORT = "No Arduino found connected to this port. Please check the port to which Arduino is connected";
    final public static String TYPE_NO_SERIAL_PORT = "No Arduino found connected to this computer. Please connect Arduino to the PC via USB";
    final public static String TYPE_PORT_USED_BY_OTHER_APP = "Unable to connect. This port is already in use by another application";
    final public static String TYPE_SEND_DATA = "This method cannot be used if the connection to Arduino was started with the arduinoRX() method, which is only for receiving data";
    final public static String TYPE_SEND_DATA_ERROR = "Error sending data";
    final public static String TYPE_WRONG_SEND_DATA_CONNECTION = "The sendData() method cannot be used if the connection to Arduino was started with the arduinoRX() method, which is only for receiving data";
    final public static String TYPE_NO_ARDUINO_CONNECTION = "No connection has been established with Arduino. Please use one of the methods arduinoRX(), arduinoTX(), or arduinoRXTX()";
    final public static String TYPE_KILL_ARDUINO_CONNECTION = "Cannot terminate the connection with Arduino if it was not started";
    final public static String TYPE_CLOSE_PORT = "Error terminating the connection with Arduino";
    final public static String TYPE_RECEIVE_DATA = "The receiveData() method cannot be used if the connection to Arduino was started with the ArduinoTX() method, which is only for sending data";
    final public static String TYPE_NO_EVENT_LISTENER = "No EventListener has been added to the PanamaHitek_Arduino class";

    // Spanish translations (default messages)
    final public static String TYPE_PORT_ALREADY_OPENED_ES = "El puerto que intenta abrir está siendo utilizado por otro dispositivo";
    final public static String TYPE_PORT_NOT_OPENED_ES = "Puerto no abierto";
    final public static String TYPE_RXTX_EXCEPTION_ES = "No se puede iniciar la conexión con Arduino 2 veces";
    final public static String TYPE_NO_ARDUINO_AT_PORT_ES = "No se ha encontrado ningún Arduino conectado a este puerto. Verifique el puerto en el que está conectado Arduino";
    final public static String TYPE_NO_SERIAL_PORT_ES = "No se ha encontrado ningún Arduino conectado a este ordenador. Por favor conecte Arduino a la PC mediante USB";
    final public static String TYPE_PORT_USED_BY_OTHER_APP_ES = "Imposible conectar. Este puerto posee una conexión con otra aplicación";
    final public static String TYPE_SEND_DATA_ES = "No es posible utilizar este método si se ha iniciado la conexión a Arduino con el método arduinoRX(), el cual es sólo para recibir datos";
    final public static String TYPE_SEND_DATA_ERROR_ES = "Error en el envío de datos";
    final public static String TYPE_WRONG_SEND_DATA_CONNECTION_ES = "No se puede utilizar el método sendData() si se ha iniciado la conexión Arduino con el método arduinoRX(), el cual es sólo para recibir datos";
    final public static String TYPE_NO_ARDUINO_CONNECTION_ES = "No se ha establecido conexión con Arduino. Por favor utilice alguno de los métodos arduinoRX(), arduinoTX() o arduinoRXTX()";
    final public static String TYPE_KILL_ARDUINO_CONNECTION_ES = "No se puede finalizar la conexión con Arduino si la misma no se ha iniciado";
    final public static String TYPE_CLOSE_PORT_ES = "Error al finalizar la conexión con Arduino";
    final public static String TYPE_RECEIVE_DATA_ES = "No se puede utilizar el método receiveData() si se ha iniciado la conexión Arduino con el método ArduinoTX(), el cual es sólo para recibir datos";
    final public static String TYPE_NO_EVENT_LISTENER_ES = "No se ha agregado un EventListener a la clase PanamaHitek_Arduino";

    /**
     * [ES] <br>
     * Establece el idioma a inglés. <br>
     * [EN] <br>
     * Sets the language to English.
     * 
     * @param englishLanguage [ES] Si es `true`, se mostrará en inglés; si es `false`, en español. <br>
     *                        [EN] If `true`, messages will be in English; if `false`, in Spanish.
     * 
     * @since 3.3.0
     */
    public static void setLanguage(boolean englishLanguage) {
        isEnglish = englishLanguage;
    }

    /**
     * [ES] <br>
     * Obtiene el mensaje de error correspondiente al tipo de excepción. <br>
     * [EN] <br>
     * Retrieves the error message corresponding to the exception type.
     * 
     * @param exceptionType [ES] Tipo de excepción. <br>
     *                      [EN] Exception type.
     * 
     * @return [ES] Mensaje de error en el idioma seleccionado. <br>
     *         [EN] Error message in the selected language.
     * 
     * @since 3.3.0
     */
    public static String getExceptionMessage(String exceptionType) {
        switch (exceptionType) {
            case "TYPE_PORT_ALREADY_OPENED":
                return isEnglish ? TYPE_PORT_ALREADY_OPENED : TYPE_PORT_ALREADY_OPENED_ES;
            case "TYPE_PORT_NOT_OPENED":
                return isEnglish ? TYPE_PORT_NOT_OPENED : TYPE_PORT_NOT_OPENED_ES;
            case "TYPE_RXTX_EXCEPTION":
                return isEnglish ? TYPE_RXTX_EXCEPTION : TYPE_RXTX_EXCEPTION_ES;
            case "TYPE_NO_ARDUINO_AT_PORT":
                return isEnglish ? TYPE_NO_ARDUINO_AT_PORT : TYPE_NO_ARDUINO_AT_PORT_ES;
            case "TYPE_NO_SERIAL_PORT":
                return isEnglish ? TYPE_NO_SERIAL_PORT : TYPE_NO_SERIAL_PORT_ES;
            case "TYPE_PORT_USED_BY_OTHER_APP":
                return isEnglish ? TYPE_PORT_USED_BY_OTHER_APP : TYPE_PORT_USED_BY_OTHER_APP_ES;
            case "TYPE_SEND_DATA":
                return isEnglish ? TYPE_SEND_DATA : TYPE_SEND_DATA_ES;
            case "TYPE_SEND_DATA_ERROR":
                return isEnglish ? TYPE_SEND_DATA_ERROR : TYPE_SEND_DATA_ERROR_ES;
            case "TYPE_WRONG_SEND_DATA_CONNECTION":
                return isEnglish ? TYPE_WRONG_SEND_DATA_CONNECTION : TYPE_WRONG_SEND_DATA_CONNECTION_ES;
            case "TYPE_NO_ARDUINO_CONNECTION":
                return isEnglish ? TYPE_NO_ARDUINO_CONNECTION : TYPE_NO_ARDUINO_CONNECTION_ES;
            case "TYPE_KILL_ARDUINO_CONNECTION":
                return isEnglish ? TYPE_KILL_ARDUINO_CONNECTION : TYPE_KILL_ARDUINO_CONNECTION_ES;
            case "TYPE_CLOSE_PORT":
                return isEnglish ? TYPE_CLOSE_PORT : TYPE_CLOSE_PORT_ES;
            case "TYPE_RECEIVE_DATA":
                return isEnglish ? TYPE_RECEIVE_DATA : TYPE_RECEIVE_DATA_ES;
            case "TYPE_NO_EVENT_LISTENER":
                return isEnglish ? TYPE_NO_EVENT_LISTENER : TYPE_NO_EVENT_LISTENER_ES;
            default:
                return isEnglish ? "Unknown exception type" : "Tipo de excepción desconocido";
        }
    }

    /**
     * [ES] <br>
     * Constructor de la clase `ArduinoException`. <br>
     * Este constructor inicializa una nueva instancia de la excepción con
     * información detallada sobre el puerto, el método que la dispara, y el
     * tipo de excepción. <br>
     * <br>
     * [EN] <br>
     * Constructor of the `ArduinoException` class. <br>
     * This constructor initializes a new instance of the exception with
     * detailed information about the port, the method that triggers it, and the
     * type of exception. <br>
     * <br>
     *
     * @param portName <br>
     * [ES] Nombre del puerto COM. <br>
     * [EN] Name of the COM port. <br>
     * <br>
     * @param methodName <br>
     * [ES] Método que dispara la excepción. <br>
     * [EN] Method that triggers the exception. <br>
     * <br>
     * @param exceptionType <br>
     * [ES] Tipo de excepción. <br>
     * [EN] Type of exception. <br>
     */
    public ArduinoException(String portName, String methodName, String exceptionType) {
        super("Nombre del puerto - " + portName + "; Nombre del método - " + methodName + "; Tipo de excepción - " + getExceptionMessage(exceptionType) + ".");
        this.portName = portName;
        this.methodName = methodName;
        this.exceptionType = exceptionType;
    }

    /**
     * [ES] <br>
     * Obtiene el valor de `exceptionType`. <br>
     * Este método devuelve el tipo de excepción registrada. <br>
     * <br>
     * [EN] <br>
     * Gets the value of `exceptionType`. <br>
     * This method returns the type of exception recorded. <br>
     * <br>
     *
     * @return <br>
     * [ES] El valor actual de `exceptionType`. <br>
     * [EN] The current value of `exceptionType`. <br>
     */
    public static String getExceptionType() {
        return exceptionType;
    }

    /**
     * [ES] <br>
     * Establece el valor de `exceptionType`. <br>
     * Este método asigna un nuevo valor al tipo de excepción, permitiendo
     * registrar información sobre un error específico. <br>
     * <br>
     * [EN] <br>
     * Sets the value of `exceptionType`. <br>
     * This method assigns a new value to the exception type, allowing the
     * recording of information about a specific error. <br>
     * <br>
     *
     * @param exceptionType <br>
     * [ES] Nuevo valor de `exceptionType`. <br>
     * [EN] New value of `exceptionType`. <br>
     */
    public static void setExceptionType(String exceptionType) {
        ArduinoException.exceptionType = exceptionType;
    }

    /**
     * [ES] <br>
     * Obtiene el valor de `methodName`. <br>
     * Este método devuelve el nombre del método en el que ocurrió la excepción.
     * <br>
     * <br>
     * [EN] <br>
     * Gets the value of `methodName`. <br>
     * This method returns the name of the method where the exception occurred.
     * <br>
     * <br>
     *
     * @return <br>
     * [ES] El valor actual de `methodName`. <br>
     * [EN] The current value of `methodName`. <br>
     */
    public static String getMethodName() {
        return methodName;
    }

    /**
     * [ES] <br>
     * Establece el valor de `methodName`. <br>
     * Este método asigna un nuevo valor al nombre del método en el cual ocurrió
     * la excepción. <br>
     * <br>
     * [EN] <br>
     * Sets the value of `methodName`. <br>
     * This method assigns a new value to the name of the method where the
     * exception occurred. <br>
     * <br>
     *
     * @param methodName <br>
     * [ES] Nuevo valor de `methodName`. <br>
     * [EN] New value of `methodName`. <br>
     */
    public static void setMethodName(String methodName) {
        ArduinoException.methodName = methodName;
    }

    /**
     * [ES] <br>
     * Obtiene el valor de `portName`. <br>
     * Este método devuelve el nombre del puerto en el que ocurrió la excepción.
     * <br>
     * <br>
     * [EN] <br>
     * Gets the value of `portName`. <br>
     * This method returns the name of the port where the exception occurred.
     * <br>
     * <br>
     *
     * @return <br>
     * [ES] El valor actual de `portName`. <br>
     * [EN] The current value of `portName`. <br>
     */
    public static String getPortName() {
        return portName;
    }

    /**
     * [ES] <br>
     * Establece el valor de `portName`. <br>
     * Este método asigna un nuevo valor al nombre del puerto en el cual ocurrió
     * la excepción. <br>
     * <br>
     * [EN] <br>
     * Sets the value of `portName`. <br>
     * This method assigns a new value to the name of the port where the
     * exception occurred. <br>
     * <br>
     *
     * @param portName <br>
     * [ES] Nuevo valor de `portName`. <br>
     * [EN] New value of `portName`. <br>
     */
    public static void setPortName(String portName) {
        ArduinoException.portName = portName;
    }

}
