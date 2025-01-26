package com.panamahitek.events;

import java.util.EventListener;

/**
 * ===================================================
 * Documentación en Español
 * ===================================================
 *
 * Interfaz `DataInsertionListener`.
 *
 * Esta interfaz define un listener para manejar eventos de inserción de datos 
 * en la aplicación. Se debe implementar en clases que requieran recibir notificaciones 
 * cuando se agregan nuevos datos a un buffer de datos mediante el evento `DataInsertionEvent`.
 *
 * ===================================================
 * Documentation in English
 * ===================================================
 *
 * Interface `DataInsertionListener`.
 *
 * This interface defines a listener to handle data insertion events in the application. 
 * It should be implemented in classes that need to receive notifications when new 
 * data is added to a data buffer through the `DataInsertionEvent`.
 *
 */
public interface DataInsertionListener extends EventListener {

    /**
     * ===================================================
     * Documentación en Español
     * ===================================================
     *
     * Método invocado cuando se detecta la inserción de datos en el buffer.
     *
     * @param ev El evento de inserción de datos que contiene la información recibida.
     *
     * ===================================================
     * Documentation in English
     * ===================================================
     *
     * Method invoked when data insertion into the buffer is detected.
     *
     * @param ev The data insertion event containing the received information.
     */
    public abstract void onDataInsertion(DataInsertionEvent ev);
}
