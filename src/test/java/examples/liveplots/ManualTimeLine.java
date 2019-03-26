package examples.liveplots;

import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_DataBuffer;
import com.panamahitek.PanamaHitek_MultiMessage;
import com.panamahitek.liveinterfaces.PanamaHitek_TimeLineChart;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;

/**
 * Este ejemplo permite graficar datos recibidos desde Arduino en la forma de un
 * grafico de linea en funcion del tiempo. Los datos son recibidos y graficados
 * manualmente en la grafica.
 *
 * @author Antony Garcia
 *
 * Utilizar con el ejemplo double_data_send.ino corriendo en el Arduino
 */
public class ManualTimeLine extends javax.swing.JFrame {

    /**
     * Se crean los objetos que permiten llevar a cabo las distintas operaciones
     * de recepcion, clasificacion, almacenamiento y grafica
     *
     * A continuacion la descripcion de los objetos:
     *
     * - ino: Objeto que permite la comunicacion entre Arduino y Java <br>
     * - multi: Recibe los datos del Arduino y los clasifica en las dos
     * variables requeridas<br>
     * - buffer: Almacena los datos recibidos de forma ordenada<br>
     * - chart: Grafica los datos almacenados en el buffer<br>
     */
    PanamaHitek_Arduino ino;
    PanamaHitek_MultiMessage multi;
    PanamaHitek_DataBuffer buffer;
    PanamaHitek_TimeLineChart chart;

    public ManualTimeLine() {
        initComponents();

        try {
            //Se inicializan las instancias de los objetos
            ino = new PanamaHitek_Arduino();
            /**
             * Se especifica que se van a recibir datos de 2 sensores
             */
            multi = new PanamaHitek_MultiMessage(2, ino);
            buffer = new PanamaHitek_DataBuffer();
            chart = new PanamaHitek_TimeLineChart();

            /**
             * Se crea un Event Listener para indicarle a Java cada vez que se
             * reciba un dato desde Arduno
             */
            SerialPortEventListener listener = new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {

                    try {
                        /**
                         * Se clasifican los datos y se insertan en el
                         * DataBuffer
                         */
                        if (multi.dataReceptionCompleted()) {

                            buffer.addValue(1, multi.getMessage(0));
                            buffer.addValue(2, multi.getMessage(1));
                            buffer.addValue(3, 50);
                            //Se imprime una fila en el buffer de datos
                            buffer.printRow();
                            /**
                             * Se limpia el buffer de recepcion para recibir un
                             * nuevo par de datos
                             */
                            multi.flushBuffer();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ManualTimeLine.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            };

            /**
             * Se establecen las columnas que formaran parte del buffer de datos
             * El indice es la posicion de la columna, seguida del nombre que se
             * le dara
             */
            buffer.addTimeColumn(0, "Tiempo");
            buffer.addColumn(1, "Temperatura", Double.class);
            buffer.addColumn(2, "Humedad", Double.class);
            buffer.addColumn(3, "LO QUE SEA", Double.class);
            
            buffer.insertToPanel(jPanel2);

            chart.setDataBuffer(buffer); //Se agrega el buffer a la grafica
            chart.setChartTitle("Temperatura y Humedad"); //Titulo de la grafica
            //Titulos de los ejes de la grafica
            chart.setAxisTitle("Tiempo", "Temperatura/Humedad");
            //Color de fondo
            chart.setBackgroundColor(Color.WHITE);
            //Color de la linea de temperatura (negro)
            chart.setLineColor(1, Color.BLUE);
            //Color de la linea de humedad (rojo)
            chart.setLineColor(2, Color.RED);

            chart.setLineColor(3, Color.GREEN);
            //Grosor de la linea de temperatura (3)
            chart.setLineThickness(1, 3);
            //Maxima cantidad de puntos en la grafica en un momento dado
            chart.setMaximumItemCount(20);
            //Se inserta la grafica en el panel
            chart.insertToPanel(jPanel1);

            //Se inicia la conexion con el Arduino
            ino.arduinoRXTX("COM21", 115200, listener);

        } catch (Exception ex) {
            Logger.getLogger(ManualTimeLine.class.getName()).log(Level.SEVERE, null, ex);
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
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 676, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 206, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManualTimeLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManualTimeLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManualTimeLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManualTimeLine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManualTimeLine().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
