/**
 * Este código es propiedad de su creador Antony García González y del Equipo
 * Creativo de Panama Hitek.
 *
 * Está protegido bajo la licencia LGPL v 2.1, cuya copia se puede encontrar en
 * el siguiente enlace: http://www.gnu.org/licenses/lgpl.txt
 *
 * Para su funcionamiento utiliza el código de la librería RXTX que ha
 * permanecido intacto sin modificación alguna de parte de nuestro equipo
 * creativo.
 *
 * Esta librería es de código abierto y ha sido diseñada para que los usuarios,
 * desde principiantes hasta expertos puedan contar con las herramientas
 * apropiadas para el desarrollo de sus proyectos, de una forma sencilla y
 * agradable.
 *
 * Se espera que se en cualquier uso de este código se reconozca su procedencia.
 * Este algoritmo fue diseñado en la República de Panamá por Antony García
 * Gónzález, estudiante de la Universidad de Panamá en la carrera de
 * Licenciatura en Ingeniería Electromecánica, desde el año 2013 hasta el
 * presente. Su diseñador forma parte del Equipo Creativo de Panama Hitek, una
 * organización sin fines de lucro dedicada a la enseñanza del desarrollo de
 * software y hardware a través de su sitio web oficial http://panamahitek.com
 *
 * Solamente deseamos que se reconozca esta compilación de código como un
 * trabajo hecho por panameños para Panamá y el mundo.
 *
 * Si desea contactarnos escríbanos a antony.garcia.gonzalez@gmail.com
 */
package com.panamahitek;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.Drivers;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Antony García González, de Proyecto Panama Hitek.
 *
 * Visita http://panamahitek.com
 */
public class PanamaHitek_Arduino {

    private OutputStream Output = null;
    private InputStream Input = null;
    private SerialPort serialPort;
    private String Connection = "";
    private String Mensaje = "";
    private boolean MessageAvailable = false;
    private boolean AvailableInUse = false;
    private boolean flag = false;
    private static boolean MessageDialogs = true;

    // Variables con los parámetros por defecto del Puerto Serie.
    private static int BYTESIZE = 8;
    private static int STOPBITS = 1;
    private static int PARITY = 0;
    private static int TIMEOUT = 2000;

    /**
     * La rutina que se ejecuta al crear la instancia de la librería
     * PanamaHitek_Arduino permite verificar si se encuentran los drivers
     * rxtxSerial.dll en una ruta que se ha establecido por defecto. En
     * versiones anteriores a la 2.7.0 la librería requería que se pre instalara
     * manualmente los drivers en la ruta de JAVA_HOME para que funcionara la
     * comunicación serial. Esto traía problemas y confusión entre algunos
     * usuarios, además de que a veces al crear JAR comprimidos se producían
     * errores en la ejecución del código. Ahora la librería PanamaHitek_Arduino
     * ha ubicado los drivers en otro directorio desde donde son cargados. Si
     * los archivos no se han colocado en dicho directorio, la propia librería
     * crea una estructura en una ruta común (Disco C en Windows. La ruta
     * completa es C:/JavaRXTX) y dentro coloca los drivers necesarios para la
     * ejecución del código, lo cual para mi concepto hace mucho más cómodo el
     * uso de esta librería.
     *
     * Actualmente este feature sólo está disponible en Windows, por lo que
     * necesitamos ayuda de usuarios de Linux para expandir estas capacidades
     * hacia otras plataformas.
     *
     */
    public PanamaHitek_Arduino() {
        PanamaHitek();
        if (System.getProperty("os.name").contains("Windows")) {
            System.out.print("Verificando archivos DLL... ");
            if (new Drivers().checkDrivers()) {
                System.out.println("OK");
            } else {
                System.out.println("Drivers no encontrados");
                new Drivers().buildDriverStructure();
                System.out.println("ATENCIÓN: La librería PanamaHitek_Arduino necesita para su funcionamiento"
                        + " la instalación de los drivers serialRXTX.dll\nen la ruta de JAVA_HOME. Estos son requisitos"
                        + " preestablecidos por la librería RXTX para comunicación serial.\nEste código ha sido optimizado"
                        + " para que cuando no se encuentren disponibles los drivers en determinado equipo se creen\ndichos ficheros,"
                        + " tanto los de 32 bits como los de 64 bits en la ruta C:/JavaRXTX. Dichos ficheros no se deben borrar ya\nque"
                        + " forman parte fundamental del funcionamiento de esta librería.");
            }
        }
 
    }

    /**
     * Este metodo simplemente imprime en la consola la información sobre la librería
     * @since v1.2.0 
     */
    private void PanamaHitek() {
        System.out.println("PanamaHitek_Arduino Library, version 2.7.0");
        System.out.println("===============================");
        System.out.println("Library created by Antony Garcia Gonzalez");
        System.out.println("Student of Panama's Tecnological University and the creator of panamahitek.com ");
        System.out.println("You can find all the information about this library at http://panamahitek.com");
        System.out.println("");

    }

    /**
     * Método para establecer la paridad en la conexión con el Puerto Serie. La
     * paridad por defecto es "Sin Paridad"
     *
     * @param input_Parity 0 = Sin Paridad, 1 = Paridad Impar, 2 =
     * Paridad Par, 3 = Paridad Marcada, 4 = Paridad Espaciada
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
     * Método para establecer el ByteSize Se aceptan valores de entrada entre 5
     * y 8.
     *
     * @param Bytes Valor tipo entero para establecer el ByteSize
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
     * Método para establecer el StopBit
     *
     * @param Bits Se establecen los StopBits
     *  1 = 1 StopBit
     *  2 = 2 StopBits
     *  3 = 1.5 StopBits
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
     * Método para establecer el TimeOut
     *
     * @param time
     * Valor tipo entero, dado en milisegundos
     * @since v2.6.0
     */
    public void setTimeOut(int time) {
        TIMEOUT = time;
    }

    /**
     * @since v2.4.0 Método para restablecer los posibles cambios en las
     * variables que se pueden producir al darse un error en la ejecución del
     * código.
     * @since v2.6.0
     */
    private void reverseChanges() {
        flag = false;
        Output = null;
        Input = null;
        Connection = "";
    }

    /**
     * Método para iniciar la conexión con Arduino, solamente para transmisión
     * de información de la computadora al Arduino por medio del Puerto Serie
     *
     * @since v1.0.0
     *
     * @param PORT_NAME Nombre del puerto en el que se encuentra conectado el
     * Arduino
     * @param DATA_RATE Velocidad de transmisión de datos en baudios por segundo
     * @throws Exception Se pueden dar varias excepciones: - Si se intenta
     * iniciar la comunicación con Arduino más de una vez - Si no se encuentra
     * ningún dispositivo conectado al Puerto Serie - Si no se encuentra un
     * Arduino conectado en el puerto establecido - Si el puerto seleccionado
     * está siendo usado por alguna otra aplicación
     *
     */
    public void arduinoTX(String PORT_NAME, int DATA_RATE) throws Exception {
        try {

            if (Connection.equals("")) {
                Connection = "TX";
            } else {
                throw new Exception("No se puede iniciar la conexión con Arduino 2 veces. Se ha usado ArduinoTX y Arduino" + Connection + " simultáneamente");
            }

            CommPortIdentifier portId = null;

            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (PORT_NAME.equals(currPortId.getName())) {
                    portId = currPortId;
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                throw new Exception("No se ha encontrado ningún Arduino conectado en el puerto " + PORT_NAME + ". Verifique el puerto en el que está conectado Arduino");
            }

            if (portId == null) {
                throw new Exception("No se ha encontrado ningún Arduino conectado a esta PC. Por favor conecte Arduino a la PC mediante USB");
            }

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
            serialPort.setSerialPortParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);

            Output = serialPort.getOutputStream();

        } catch (PortInUseException e) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
            }
            throw new Exception("Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
        } catch (Exception ex) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Método para iniciar la conexión con Arduino, solamente para recepción de
     * información de en la computadora desde el Arduino por medio del Puerto
     * Serie
     *
     * @since v1.0.0
     *
     * @param PORT_NAME Nombre del puerto en el que se encuentra conectado el
     * Arduino
     * @param DATA_RATE Velocidad de transmisión de datos en baudios por segundo
     * @param events Instancia de la clase SerialPortEventListener para detectar
     * cuando sea que se recibe información en el Puerto Serie
     * @throws Exception Se pueden dar varias excepciones: - Si se intenta
     * iniciar la comunicación con Arduino más de una vez - Si no se encuentra
     * ningún dispositivo conectado al Puerto Serie - Si no se encuentra un
     * Arduino conectado en el puerto establecido - Si el puerto seleccionado
     * está siendo usado por alguna otra aplicación
     */
    public void arduinoRX(String PORT_NAME, int DATA_RATE, SerialPortEventListener events) throws Exception {

        try {

            if (Connection.equals("")) {
                Connection = "RX";
            } else {
                throw new Exception("No se puede iniciar la conexión con Arduino 2 veces. Se ha usado ArduinoRX y Arduino" + Connection + " simultáneamente");
            }
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (PORT_NAME.equals(currPortId.getName())) {
                    portId = currPortId;
                    flag = true;
                    break;
                }

            }
            if (flag == false) {
                throw new Exception("No se ha encontrado ningún Arduino conectado en el puerto " + PORT_NAME + ". Verifique el puerto en el que está conectado Arduino");
            }
            if (portId == null) {
                throw new Exception("No se ha encontrado ningún Arduino conectado a esta PC. Por favor conecte Arduino a la PC mediante USB");
            }

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
            serialPort.setSerialPortParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);

            Input = serialPort.getInputStream();

            serialPort.addEventListener((SerialPortEventListener) events);
            serialPort.notifyOnDataAvailable(true);

        } catch (PortInUseException e) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
            }
            throw new Exception("Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");

        } catch (Exception ex) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            throw new Exception(ex.getMessage());
        }

    }

    /**
     * Método para iniciar la conexión con Arduino,tanto para enviar como para
     * recibir información desde el Arduino por medio del Puerto Serie
     *
     * @since v1.0.0
     *
     * @param PORT_NAME Nombre del puerto en el que se encuentra conectado el
     * Arduino
     * @param DATA_RATE Velocidad de transmisión de datos en baudios por segundo
     * @param events Instancia de la clase SerialPortEventListener para detectar
     * cuando sea que se recibe información en el Puerto Serie
     * @throws Exception Se pueden dar varias excepciones: - Si se intenta
     * iniciar la comunicación con Arduino más de una vez - Si no se encuentra
     * ningún dispositivo conectado al Puerto Serie - Si no se encuentra un
     * Arduino conectado en el puerto establecido - Si el puerto seleccionado
     * está siendo usado por alguna otra aplicación
     *
     *
     *
     */
    public void arduinoRXTX(String PORT_NAME, int DATA_RATE, SerialPortEventListener events) throws Exception {
        System.out.println("+++"+this.getClass().getName());
        try {

            if (Connection.equals("")) {
                Connection = "RXTX";
            } else {
                throw new Exception("No se puede iniciar la conexión con Arduino 2 veces. Se ha usado ArduinoRXTX y Arduino" + Connection + " simultáneamente");
            }
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (PORT_NAME.equals(currPortId.getName())) {
                    portId = currPortId;
                    flag = true;
                    break;
                }

            }
            if (flag == false) {
                throw new Exception("No se ha encontrado ningún Arduino conectado en el puerto " + PORT_NAME + ". Verifique el puerto en el que está conectado Arduino");
            }

            if (portId == null) {
                throw new Exception("No se ha encontrado ningún Arduino conectado a esta PC. Por favor conecte Arduino a la PC mediante USB");
            }

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
            serialPort.setSerialPortParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);

            Input = serialPort.getInputStream();
            Output = serialPort.getOutputStream();
            serialPort.addEventListener((SerialPortEventListener) events);
            serialPort.notifyOnDataAvailable(true);
        } catch (PortInUseException e) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
            }
            throw new Exception("Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");

        } catch (Exception ex) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Método para el envío de información desde Java hacia Arduino. 
     * 
     * @since v1.0.0
     *
     * @param data Una cadena de caracteres con el String que se desea enviar.
     * @throws Exception Se puede producir un error si no se ha iniciado la
     * conexión con Aruino o si se intenta enviar datos cuando se ha iniciado la
     * conexión por medio de ArduinoRX().
     *
     */
    public void sendData(String data) throws Exception {
        try {

            if (Connection.equals("RX")) {
                throw new Exception("No se puede utilizar el método SendData() si se ha iniciado la conexión Arduino con el método ArduinoRX(), el cual es sólo para recibir datos");
            } else if (Connection.equals("")) {
                throw new Exception("No se ha iniciado conexión con Arduino. Por favor utilice alguno de los métodos ArduinoRX(), ArduinoTX() o ArduinoRXTX()");
            } else if (Connection.equals("TX") || Connection.equals("RXTX")) {

                Output.write(data.getBytes());
            }

        } catch (IOException e) {
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Error en el envío de datos");
            }
            throw new Exception("Error en el envío de datos");

        } catch (Exception e) {
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            throw new Exception(e.getMessage());

        }
    }

    /**
     * Se envía información desde Java hacia Arduino en forma de Bytes, a
     * diferencia del método sendData(String Data) donde la información se envía
     * como una cadena de caracteres (String).
     *
     * @param data El Byte que se desea enviar (Un valor entre 0 y 255)
     * @throws Exception Se puede producir una excepción si no se ha iniciado la
     * conexión con Arduino o si dicha conexión se ha iniciado a través del
     * método ArduinoRX().
     *
     *
     * @since v2.6.0
     */
    public void sendByte(int data) throws Exception {
        try {

            if (Connection.equals("RX")) {
                throw new Exception("No se puede utilizar el método SendData() si se ha iniciado la conexión Arduino con el método ArduinoRX(), el cual es sólo para recibir datos");
            } else if (Connection.equals("")) {
                throw new Exception("No se ha iniciado conexión con Arduino. Por favor utilice alguno de los métodos ArduinoRX(), ArduinoTX() o ArduinoRXTX()");
            } else if (Connection.equals("TX") || Connection.equals("RXTX")) {
                Output.write(data);
            }

        } catch (IOException e) {
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Error en el envío de datos");
            }
            throw new Exception("Error en el envío de datos");

        } catch (Exception e) {
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Método para recibir datos desde Arduino. Se reciben los datos tal como
     * son enviados desde Arduino, caracter por caracter.
     *
     * @return Un entero entre 0 y 255 con el caracter que se haya recibido
     * @throws Exception Se puede producir una excepción si no se ha iniciado la
     * conexión con Arduino o si la misma ha sido iniciada utilizando el método
     * ArduinoTX().
     *
     *
     * @since v1.0.0
     */
    public int receiveData() throws Exception {

        int Salida = 0;
        try {
            if (Connection.equals("TX")) {
                throw new Exception("No se puede utilizar el método ReceiveData() si se ha iniciado la conexión Arduino con el método ArduinoTX(), el cual es sólo para recibir datos");
            } else if (Connection.equals("")) {

                throw new Exception("No se ha iniciado conexión con Arduino. Por favor utilice alguno de los métodos ArduinoRX(), ArduinoTX() o ArduinoRXTX()");
            } else if ((Connection.equals("RX")) || (Connection.equals("RXTX"))) {

                Salida = Input.read();
            }

        } catch (IOException e) {
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Error en la recepción de datos");
            }
            throw new Exception("Error en la recepción de datos");

        } catch (Exception e) {
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            throw new Exception(e.getMessage());

        }
        return Salida;
    }

    /**
     * Método para hacer la lectura de datos enviados desde Arduino con
     * Serial.println(). Se busca cuando se produce un salto de línea y se
     * imprime la información.
     */
    private void serialRead() {
        int entrada;
        try {
            if (MessageAvailable == false) {
                entrada = receiveData();
                if (entrada > 0) {
                    if (entrada != 13) {
                        if (entrada != 10) {
                            Mensaje = Mensaje + (char) entrada;
                        } else {
                            MessageAvailable = true;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(PanamaHitek_Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devuelve un valor true cuando se produce un salto de línea
     * en el envío de información desde Arduino hacia la computadora. Se debe
     * tomar en cuenta que la separación entre un mensaje y otro depende del uso
     * de Serial.println() en Arduino, ya que este método busca los saltos de
     * línea en los mensajes para luego llevar a cabo la impresión. Si se
     * utiliza Serial.print() la librería no reconocerá el mensaje que se esté
     * enviando.
     *
     * @since v2.6.0
     * 
     * @return Una variable tipo boolean que será TRUE cuando se reciba un salto
     * de línea en la recepción de datos desde Arduino.
     */
    public boolean isMessageAvailable() {
        AvailableInUse = true;
        serialRead();
        boolean Salida = MessageAvailable;
        return Salida;
    }

    /**
     * @since v2.0.0
     * @deprecated Este método ahora se llama isMessageAvailable() Devuelve un
     * valor true cuando se produce un salto de línea en el envío de información
     * desde Arduino hacia la computadora. Se debe tomar en cuenta que la
     * separación entre un mensaje y otro depende del uso de Serial.println() en
     * Arduino, ya que este método busca los saltos de línea en los mensajes
     * para luego llevar a cabo la impresión. Si se utiliza Serial.print() la
     * librería no reconocerá el mensaje que se esté enviando.
     *
     * @return Una variable tipo boolean que será TRUE cuando se reciba un salto
     * de línea en la recepción de datos desde Arduino.
     */
    public boolean MessageAvailable() {
        AvailableInUse = true;
        serialRead();
        boolean Salida = MessageAvailable;
        return Salida;
    }

    /**
     * Este método se utiliza dentro de una estructura condicional. Cuando el
     * método isMessageAvailable() devuelve un valor TRUE, este método imprime
     * el mensaje que haya sido almacenado en el buffer.
     *
     * @since v2.0.0
     *
     * @return Un String con el mensaje que se haya recibido
     */
    public String printMessage() {
        String Salida = "No hay datos disponibles";
        if (AvailableInUse == false) {
            serialRead();
        }
        if (isMessageAvailable() == true) {
            Salida = Mensaje;
            Mensaje = "";
            MessageAvailable = false;
        }
        return Salida;
    }

    /**
     * @deprecated Este método ha sido renombrado. Desde la versión 2.6.0 de la
     * librería PanamaHitek_Arduino se utiliza getportsAvailable().     
     * Devuelve la cantidad de puertos activos. Esto dependerá de cuantos
     * dispositivos dispositivos estén conectado a a la computadora a través del
     * Puerto Serie
     *
     * @return Una número entero positivo mayor o igual a cero con la cantidad
     * de puertos que se encontraron disponibles.
     * 
     * 
     */
    public int SerialPortsAvailable() {
        int Output = 0;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier currPortId;
        while (portEnum.hasMoreElements()) {
            Output++;
            currPortId = (CommPortIdentifier) portEnum.nextElement();
        }

        return Output;
    }

    /**
     * 
     *
     * Devuelve la cantidad de puertos activos. Esto dependerá de cuantos
     * dispositivos dispositivos estén conectado a a la computadora a través del
     * Puerto Serie
     * 
     * @since v2.6.0
     *
     * @return Una número entero positivo mayor o igual a cero con la cantidad
     * de puertos que se encontraron disponibles.
     */
    public int getPortsAvailable() {
        int Output = 0;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier currPortId;
        while (portEnum.hasMoreElements()) {
            Output++;
            currPortId = (CommPortIdentifier) portEnum.nextElement();
        }
        return Output;
    }

    /**
     * Devuelve los puertos disponibles en el Puerto Serie. Si el método
     * SerialPortsAvailable() devuelve un valor mayor que 0 entonces se puede
     * obtener el nombre de un puerto en específico utilizando su índice que es
     * el la ubicación de dicho puerto entre los puertos que se encuentren
     * disponibles.
     *
     * @deprecated Este método ha sido reemplazado por getSerialPorts() desde la
     * versión 2.6.0 de la librería PanamaHitek_Arduino.
     * @param index Número del puerto cuyo nomre se desea obtener. Este número
     * es el puesto que dicho puerto ocuoa entre los puertos disponibles.
     * @return Un String con el nombre del puerto cuyo índice se introdujo como
     * parámetro
     */
    public String NameSerialPortAt(int index) {
        String Output = "";
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier currPortId = null;
        if (SerialPortsAvailable() > 0) {
            for (int i = 1; i <= index; i++) {
                currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (i == index) {
                    Output += currPortId.getName();
                } else {
                    Output += currPortId.getName() + ",";
                }
            }

        } else {
            Output = "NO HAY PUERTOS COM CONECTADOS";
        }

        return Output;
    }

    /**
     * Nombra los puertos disponibles en el Puerto Serie
     *
     * @return Una lista con los puertos disponibles
     *
     * @since v2.6.0
     */
    public List<String> getSerialPorts() {
        List<String> ports = new ArrayList<String>();
        if (getPortsAvailable() > 0) {
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                ports.add(currPortId.getName());
            }
        }
        return ports;
    }

    /**
     * Método para finalizar la conexión con Arduino en el puerto serie.
     *
     * @throws Exception Se puede producir una excepción cuando se intenta
     * cerrar la conexión con Arduino sin que la misma se haya iniciado antes
     *
     * @since v2.0.0
     */
    public void killArduinoConnection() throws Exception {
        try {

            if (Connection.equals("")) {
                throw new Exception("No se puede finalizar la conexión con Arduino si la misma no se ha iniciado");
            } else {
                serialPort.close();
                Connection = "";
                System.out.println("Conexión con Arduino Finalizada");
            }
        } catch (Exception e) {

            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } else {
                throw new Exception(e.getMessage());
            }
        }

    }

    /**
     * Activa o desactiva los MessageDialogs que son las ventanas emergentes que
     * se muestran cuando se produce un error. Algunos usuarios de la librería
     * se quejaban de que eran molestas para ellos así que se decidió volverlas
     * opcionales.
     *
     * @since v2.5.0
     *
     * @param ON_or_OFF Variable tipo boolean que indica si se desea activar las
     * ventanas emergentes o no.
     */
    public void showMessageDialogs(boolean ON_or_OFF) {
        MessageDialogs = ON_or_OFF;
    }

    /**
     * Método para iniciar la conexión con Arduino, solamente para transmisión
     * de información de la computadora al Arduino por medio del Puerto Serie
     *
     * @since v1.0.0
     * @deprecated Desde la version 2.6.0 no es necesario establecer el TIME_OUT
     * como parámetro de entrada. Si se desea establecer un TIME_OUT distinto
     * del establecido por defecto (2000 milisegundos) se debe utilizar el
     * método setTimeOut(int time).
     * @param PORT_NAME Nombre del puerto en el que se encuentra conectado el
     * Arduino
     * @param TIME_OUT Tiempo máximo que esperará el programa para establecer la
     * comunicación serial con el dispositivo
     * @param DATA_RATE Velocidad de transmisión de datos en baudios por segundo
     * @throws Exception Se pueden dar varias excepciones: - Si se intenta
     * iniciar la comunicación con Arduino más de una vez - Si no se encuentra
     * ningún dispositivo conectado al Puerto Serie - Si no se encuentra un
     * Arduino conectado en el puerto establecido - Si el puerto seleccionado
     * está siendo usado por alguna otra aplicación
     *
     *
     */
    public void ArduinoTX(String PORT_NAME, int TIME_OUT, int DATA_RATE) throws Exception {
        try {

            if (Connection.equals("")) {
                Connection = "TX";
            } else {
                throw new Exception("No se puede iniciar la conexión con Arduino 2 veces. Se ha usado ArduinoTX y Arduino" + Connection + " simultáneamente");
            }

            CommPortIdentifier portId = null;

            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (PORT_NAME.equals(currPortId.getName())) {
                    portId = currPortId;
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                throw new Exception("No se ha encontrado ningún Arduino conectado en el puerto " + PORT_NAME + ". Verifique el puerto en el que está conectado Arduino");
            }

            if (portId == null) {
                throw new Exception("No se ha encontrado ningún Arduino conectado a esta PC. Por favor conecte Arduino a la PC mediante USB");
            }

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
            serialPort.setSerialPortParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);

            Output = serialPort.getOutputStream();

        } catch (PortInUseException e) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
            }
            throw new Exception("Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
        } catch (Exception ex) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * Método para iniciar la conexión con Arduino, solamente para recepción de
     * información de en la computadora desde el Arduino por medio del Puerto
     * Serie
     *
     * @since v1.0.0
     * @deprecated Desde la version 2.6.0 no es necesario establecer el TIME_OUT
     * como parámetro de entrada. Si se desea establecer un TIME_OUT distinto
     * del establecido por defecto (2000 milisegundos) se debe utilizar el
     * método setTimeOut(int time).
     * @param PORT_NAME Nombre del puerto en el que se encuentra conectado el
     * Arduino
     * @param TIME_OUT Tiempo máximo que esperará el programa para establecer la
     * comunicación serial con el dispositivo
     * @param DATA_RATE Velocidad de transmisión de datos en baudios por segundo
     * @param events Instancia de la clase SerialPortEventListener para detectar
     * cuando sea que se recibe información en el Puerto Serie
     * @throws Exception Se pueden dar varias excepciones: - Si se intenta
     * iniciar la comunicación con Arduino más de una vez - Si no se encuentra
     * ningún dispositivo conectado al Puerto Serie - Si no se encuentra un
     * Arduino conectado en el puerto establecido - Si el puerto seleccionado
     * está siendo usado por alguna otra aplicación
     *
     *
     */
    public void ArduinoRX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener events) throws Exception {

        try {

            if (Connection.equals("")) {
                Connection = "RX";
            } else {
                throw new Exception("No se puede iniciar la conexión con Arduino 2 veces. Se ha usado ArduinoRX y Arduino" + Connection + " simultáneamente");
            }
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (PORT_NAME.equals(currPortId.getName())) {
                    portId = currPortId;
                    flag = true;
                    break;
                }

            }
            if (flag == false) {
                throw new Exception("No se ha encontrado ningún Arduino conectado en el puerto " + PORT_NAME + ". Verifique el puerto en el que está conectado Arduino");
            }
            if (portId == null) {
                throw new Exception("No se ha encontrado ningún Arduino conectado a esta PC. Por favor conecte Arduino a la PC mediante USB");
            }

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
            serialPort.setSerialPortParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);

            Input = serialPort.getInputStream();

            serialPort.addEventListener((SerialPortEventListener) events);
            serialPort.notifyOnDataAvailable(true);

        } catch (PortInUseException e) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
            }
            throw new Exception("Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");

        } catch (Exception ex) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            throw new Exception(ex.getMessage());
        }

    }

    /**
     * Método para iniciar la conexión con Arduino,tanto para enviar como para
     * recibir información desde el Arduino por medio del Puerto Serie
     *
     * @since v1.0.0
     * @deprecated Desde la version 2.6.0 no es necesario establecer el TIME_OUT
     * como parámetro de entrada. Si se desea establecer un TIME_OUT distinto
     * del establecido por defecto (2000 milisegundos) se debe utilizar el
     * método setTimeOut(int time).
     * @param PORT_NAME Nombre del puerto en el que se encuentra conectado el
     * Arduino
     * @param TIME_OUT Tiempo máximo que esperará el programa para establecer la
     * comunicación serial con el dispositivo
     * @param DATA_RATE Velocidad de transmisión de datos en baudios por segundo
     * @param events Instancia de la clase SerialPortEventListener para detectar
     * cuando sea que se recibe información en el Puerto Serie
     * @throws Exception Se pueden dar varias excepciones: - Si se intenta
     * iniciar la comunicación con Arduino más de una vez - Si no se encuentra
     * ningún dispositivo conectado al Puerto Serie - Si no se encuentra un
     * Arduino conectado en el puerto establecido - Si el puerto seleccionado
     * está siendo usado por alguna otra aplicación
     *
     *
     *
     */
    public void ArduinoRXTX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener events) throws Exception {

        try {

            if (Connection.equals("")) {
                Connection = "RXTX";
            } else {
                throw new Exception("No se puede iniciar la conexión con Arduino 2 veces. Se ha usado ArduinoRXTX y Arduino" + Connection + " simultáneamente");
            }
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            while (portEnum.hasMoreElements()) {
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                if (PORT_NAME.equals(currPortId.getName())) {
                    portId = currPortId;
                    flag = true;
                    break;
                }

            }
            if (flag == false) {
                throw new Exception("No se ha encontrado ningún Arduino conectado en el puerto " + PORT_NAME + ". Verifique el puerto en el que está conectado Arduino");
            }

            if (portId == null) {
                throw new Exception("No se ha encontrado ningún Arduino conectado a esta PC. Por favor conecte Arduino a la PC mediante USB");
            }

            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIMEOUT);
            serialPort.setSerialPortParams(DATA_RATE, BYTESIZE, STOPBITS, PARITY);

            Input = serialPort.getInputStream();
            Output = serialPort.getOutputStream();
            serialPort.addEventListener((SerialPortEventListener) events);
            serialPort.notifyOnDataAvailable(true);
        } catch (PortInUseException e) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, "Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");
            }
            throw new Exception("Imposible Conectar. El puerto " + PORT_NAME + " está siendo usado por otra aplicación");

        } catch (Exception ex) {
            reverseChanges();
            if (MessageDialogs) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            throw new Exception(ex.getMessage());
        }
    }

}
