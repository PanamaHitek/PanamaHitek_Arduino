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
 * @author Antony Garcia Gonzalez
 *
 * <br>Un codigo que permite recibir multiples datos simultaneos datos desde el
 * Arduino. Debe ser utilizado con el codigo multiple_data_semd.ino
 */
public class rxMultiple {
//Se crea una variable tipo PanamaHitek_Arduino

    static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    /**
     * Se crea una instancia de la clase PanamaHitek_MultiMessage, indicando la
     * implementacion de 4 sensores
     */
    static PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(4, ino);
    //Se crea un eventListener para el puerto serie
    static SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        //Si se recibe algun dato en el puerto serie, se ejecuta el siguiente metodo
        public void serialEvent(SerialPortEvent serialPortEvent) {
            try {
                /*
                Los datos en el puerto serie se envian caracter por caracter. Si se
                desea esperar a terminar de recibir el mensaje antes de imprimirlo, 
                el metodo dataReceptionCompleted() devolvera TRUE cuando se haya terminado
                de recibir el mensaje, el cual podra ser impreso a traves del metodo
                printMessage()
                 */
                if (multi.dataReceptionCompleted()) {
                    /**
                     * Se imprimen los datos recibidos del Arduino en el mismo
                     * orden en el que son enviados. Los indices 0, 1, 2 y 3
                     * indican el cual dato se debe imprimir
                     */
                    System.out.println("Sensor 1: " + multi.getMessage(0));
                    System.out.println("Sensor 2: " + multi.getMessage(1));
                    System.out.println("Sensor 3: " + multi.getMessage(2));
                    System.out.println("Sensor 4: " + multi.getMessage(3));
                    System.out.println("_______________________________"); //Separador

                    /*
                    Cuando se ha terminado de imprimir los datos, se invoca el 
                    flushBuffer para que se pueda recibir el siguiente set de datos
                     */
                    multi.flushBuffer();
                }
            } catch (SerialPortException ex) {
                Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public static void main(String[] args) {
        try {
            //Se inicializa la conexion con el Arduino en el puerto COM5
            ino.arduinoRX("COM5", 9600, listener);
        } catch (ArduinoException ex) {
            Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(rxMultiple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
