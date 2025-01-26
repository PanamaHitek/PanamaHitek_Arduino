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
package com.panamahitek.events;

import com.panamahitek.PanamaHitek_DataBuffer;
import java.util.EventObject;

/**
 * [ES] <br>
 * Esta clase representa un evento que se dispara cuando se insertan nuevos
 * datos en el buffer de datos `PanamaHitek_DataBuffer`. <br>
 * Extiende de la clase `EventObject`, lo que permite la gestión de eventos
 * relacionados con la recepción de datos en aplicaciones que utilizan la
 * biblioteca PanamaHitek. <br>
 * <br>
 * La clase almacena una referencia al buffer de datos asociado con el evento,
 * proporcionando acceso a la información recién agregada. <br>
 * <br>
 * [EN] <br>
 * This class represents an event that is triggered when new data is inserted
 * into the `PanamaHitek_DataBuffer`. <br>
 * It extends the `EventObject` class, enabling the management of data reception
 * events in applications using the PanamaHitek library. <br>
 * <br>
 * The class stores a reference to the data buffer associated with the event,
 * providing access to the newly added information. <br>
 * <br>
 *
 * @autor Antony García González
 */
public class DataInsertionEvent extends EventObject {

    PanamaHitek_DataBuffer buffer;

    /**
     * [ES] <br>
     * Constructor de la clase `DataInsertionEvent`. <br>
     * Inicializa un nuevo evento de inserción de datos, que se dispara cuando
     * se agregan datos al buffer de datos `PanamaHitek_DataBuffer`. <br>
     * <br>
     * [EN] <br>
     * Constructor of the `DataInsertionEvent` class. <br>
     * Initializes a new data insertion event, which is triggered when data is
     * added to the `PanamaHitek_DataBuffer`. <br>
     * <br>
     *
     * @param source <br>
     * [ES] El objeto fuente que generó el evento. <br>
     * [EN] The source object that generated the event. <br>
     * <br>
     * @param buffer <br>
     * [ES] El buffer de datos que contiene la información insertada. <br>
     * [EN] The data buffer containing the inserted information. <br>
     */
    public DataInsertionEvent(Object source, PanamaHitek_DataBuffer buffer) {
        super(source);
        this.buffer = buffer;
    }

    /**
     * [ES] <br>
     * Obtiene el buffer de datos asociado a este evento. <br>
     * <br>
     * [EN] <br>
     * Gets the data buffer associated with this event. <br>
     * <br>
     *
     * @return <br>
     * [ES] Una instancia de `PanamaHitek_DataBuffer` con la información
     * recibida. <br>
     * [EN] An instance of `PanamaHitek_DataBuffer` containing the received
     * information. <br>
     */
    public PanamaHitek_DataBuffer getBuffer() {
        return buffer;
    }
}
