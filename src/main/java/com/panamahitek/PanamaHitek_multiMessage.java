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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antony García González, de Proyecto Panama Hitek. Visita
 * http://panamahitek.com
 */
public class PanamaHitek_multiMessage {

    //Variables 
    private int Mensajes = 0;
    private int lecturas = 0;
    private List<String> buffer;
    private boolean ReceptionCompleted = false;
    private PanamaHitek_Arduino ino;
    PanamaHitek_Arduino Arduino = new PanamaHitek_Arduino();
    /*Esta clase ha sido diseñada para hacer lectura de múltiples datos, por ejemplo
     de sensores conectados a Arduino sin tener que llevar a cabo complicadas secuencias lógicas
     para discernir entre una lectura y otra.
     */

    public PanamaHitek_multiMessage(int Messages, PanamaHitek_Arduino InputObject) {
        ino = InputObject;
        Mensajes = Messages;
        buffer = new ArrayList<String>();
    }

    /**
     * Este método revisa constantemente si se ha terminado de leer la cantidad
     * de mensajes establecida en la creación del objeto de la clase
     * PanamaHitek_multiMessage.
     *
     * @return TRUE si se ha terminado de leer datos, FALSE si aún no se
     * completa la lectura.
     */
    public boolean DataReceptionCompleted() {

        if (!ReceptionCompleted) {
            if (ino.isMessageAvailable()) {
                buffer.add(ino.printMessage());
                lecturas++;
            }
            if (lecturas == Mensajes) {
                ReceptionCompleted = true;
            }

        }

        return ReceptionCompleted;
    }

    /**
     * Método para obtener la información leída.
     *
     * @param index Indica el índice que se desea leer. Este está dado por el
     * orden en que se imprimen los datos en el Serial.println() de Arduino
     * @return un String con la información solicitada
     */
    public String getMessage(int index) {

        String Output = buffer.get(index);
        return Output;
    }

    /**
     *
     * @return Este método devuelve una lista con los mensajes recibidos en
     * determinada lectura
     */
    public List<String> getMessageList() {
        return buffer;
    }

    /**
     * Este método se encarga de limpiar el buffer y restablecer las variables
     * para prepararse para una nueva lectura
     */
    public void flushBuffer() {
        lecturas = 0;
        ReceptionCompleted = false;
        buffer.clear();
    }
}
