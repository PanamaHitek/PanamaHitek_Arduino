/**
 * Este código ha sido construido por Antony García González y el Equipo
 * Creativo de Panama Hitek.
 *
 * Está protegido bajo la licencia LGPL v 2.1, cuya copia se puede encontrar en
 * el siguiente enlace: http://www.gnu.org/licenses/lgpl.txt
 *
 * Para su funcionamiento utiliza el código de la librería JSSC (anteriormente
 * RXTX) que ha permanecido intacto sin modificación alguna de parte de nuestro
 * equipo creativo. Agradecemos al creador de la librería JSSC, Alexey Sokolov
 * por esta herramienta tan poderosa y eficaz que ha hecho posible el
 * mejoramiento de nuestra librería.
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
 * Si desea contactarnos escríbanos a creativeteam@panamahitek.com
 */
package examples.excel;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_DataBuffer;
import com.panamahitek.PanamaHitek_MultiMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 * Este ejemplo permite conectarse con Arduino y empezar a recibir datos. Estos
 * datos seran tabulados en un JTable y mostrados en pantalla. Al presionar el
 * boton disponible en la interfaz, los datos tabulados seran exportados a un
 * archivo en Excel.
 *
 * Para utilizar este ejemplo se requiere que en Arduino se haya subido el
 * ejemplo double_data_send.ino
 *
 * @author Antony García González, de Proyecto Panama Hitek. Visita
 * http://panamahitek.com
 */
public class ExcelExport extends javax.swing.JFrame {

    //Objeto para la gestion de la conexion con Arduino
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    //Objeto para la gestion de multiples mensajes recibidos desde Arduino
    PanamaHitek_MultiMessage multi = new PanamaHitek_MultiMessage(2, ino);
    //Objeto para la gestion y almacenamiento de datos recibidos
    PanamaHitek_DataBuffer buffer = new PanamaHitek_DataBuffer();

    public ExcelExport() {
        initComponents();

        /**
         * Se crea la tabla de datos, agregando 3 columnas. Se especifica la
         * posicion de la columna (0 es la más a la izquierda), el nombre de la
         * columna y el tipo de datos
         */
        buffer.addTimeColumn(0, "Tiempo");
        buffer.addColumn(1, "Temperatura", Double.class);
        buffer.addColumn(2, "Humedad", Double.class);

        //Se inserta la tabla en un panel para poder verla en la interfaz
        buffer.insertToPanel(jPanel1);

        /**
         * Con este objeto se gestiona la recepcion de datos. El evento
         * serialEvent se "disparara" cada vez que se reciba un dato desde
         * Arduino enviado a traves del puerto serie
         */
        SerialPortEventListener listener = new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                try {
                    /**
                     * Este metodo indica cuando se haya terminado de recibir
                     * los datos de los DOS sensores especificados en la
                     * creacion del objeto "multi"
                     */
                    if (multi.dataReceptionCompleted()) {
                        /**
                         * Se agregan los valores al buffer, especificando el
                         * índice de la columna y el valor. Se utiliza el objeto
                         * "multi" para separar los datos recibidos desde
                         * Arduino, especificando el indice.
                         */

                        buffer.addValue(1, Double.parseDouble(multi.getMessage(0)));
                        buffer.addValue(2, Double.parseDouble(multi.getMessage(1)));

                        //Se salta un "renglon" en el buffer de datos
                        buffer.printRow();
                        /**
                         * Se le indica al objeto multi que se ha terminado de
                         * imprimir los datos recibidos desde el Arduino y que
                         * puede prepararse para recibir un nuevo par de datos
                         */
                        multi.flushBuffer();
                    }
                } catch (ArduinoException ex) {
                    Logger.getLogger(ExcelExport.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(ExcelExport.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ExcelExport.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        try {
            //Se inicia la conexion con el puerto COM21 a 9600 baudios
            ino.arduinoRX("COM28", 9600, listener);
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(ExcelExport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("Exportar a Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jButton1)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(4, 4, 4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /**
         * Este boton permite finalizar la conexion con Arduino y exportar los
         * datos a Excel
         */
        try {
            //Se exportan los datos a Excel
            buffer.exportExcelFile();
            //Se finaliza la conexion con Arduino
            ino.killArduinoConnection();
            //Se muestra un mensaje
            JOptionPane.showMessageDialog(this, "Conexión con Arduino Finalizada");
            //Se cierra el programa
            System.exit(0);
        } catch (ArduinoException | IOException ex) {
            Logger.getLogger(ExcelExport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ExcelExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExcelExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExcelExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExcelExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExcelExport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
