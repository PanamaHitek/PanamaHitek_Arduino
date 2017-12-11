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
package com.panamahitek.liveinterfaces;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_MultiMessage;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * Esta clase construye un grafico tipo reloj en la que se pueden graficar datos
 * recibidos desde Arduino en tiempo real
 *
 * 
 * @author Antony García González, de Proyecto Panama Hitek. Visita
 * http://panamahitek.com
 * @since 3.0.0
 */
public class PanamaHitek_DualDialPlot extends JPanel {

    public final static int OUTER_DIAL = 1;
    public final static int INNER_DIAL = 2;
    public final static int LEFT_DIAL = 1;
    public final static int RIGHT_DIAL = 2;

    private static plotPanel plot;
    private static PanamaHitek_Arduino ino = null;
    private static PanamaHitek_MultiMessage multi;

    private class plotPanel extends JPanel {

        //Dataset donde se guardan los datos
        private DefaultValueDataset dataset1;
        private DefaultValueDataset dataset2;
        //Titulo de la grafica
        private String plotTitle = "Default Title";
        //Nombre de la variable a graficar
        private String variableName = "Default Variable Name";
        private int plotOuterBottonLimit = 0; //Limite inferior de la grafica
        private int plotOuterTopLimit = 100; //Limite superior de la grafica
        private int plotInnerBottonLimit = 0;
        private int plotInnerTopLimit = 100;
        private int outerMinorDivisions = 4; //Divisiones menores
        private int outerMajorDivisions = 20;//Divisiones mayores
        private int innerMinorDivisions = 4; //Divisiones menores
        private int innerMajorDivisions = 20;//Divisiones mayores

        public DefaultValueDataset getDataset1() {
            return dataset1;
        }

        public void setDataset1(DefaultValueDataset dataset1) {
            this.dataset1 = dataset1;
        }

        public DefaultValueDataset getDataset2() {
            return dataset2;
        }

        public void setDataset2(DefaultValueDataset dataset2) {
            this.dataset2 = dataset2;
        }

        public String getPlotTitle() {
            return plotTitle;
        }

        public void setPlotTitle(String plotTitle) {
            this.plotTitle = plotTitle;
        }

        public String getVariableName() {
            return variableName;
        }

        public void setVariableName(String variableName) {
            this.variableName = variableName;
        }

        public int getPlotOuterBottonLimit() {
            return plotOuterBottonLimit;
        }

        public void setPlotOuterBottonLimit(int plotOuterBottonLimit) {
            this.plotOuterBottonLimit = plotOuterBottonLimit;
        }

        public int getPlotOuterTopLimit() {
            return plotOuterTopLimit;
        }

        public void setPlotOuterTopLimit(int plotOuterTopLimit) {
            this.plotOuterTopLimit = plotOuterTopLimit;
        }

        public int getPlotInnerBottonLimit() {
            return plotInnerBottonLimit;
        }

        public void setPlotInnerBottonLimit(int plotInnerBottonLimit) {
            this.plotInnerBottonLimit = plotInnerBottonLimit;
        }

        public int getPlotInnerTopLimit() {
            return plotInnerTopLimit;
        }

        public void setPlotInnerTopLimit(int plotInnerTopLimit) {
            this.plotInnerTopLimit = plotInnerTopLimit;
        }

        public int getOuterMinorDivisions() {
            return outerMinorDivisions;
        }

        public void setOuterMinorDivisions(int outerMinorDivisions) {
            this.outerMinorDivisions = outerMinorDivisions;
        }

        public int getOuterMajorDivisions() {
            return outerMajorDivisions;
        }

        public void setOuterMajorDivisions(int outerMajorDivisions) {
            this.outerMajorDivisions = outerMajorDivisions;
        }

        public int getInnerMinorDivisions() {
            return innerMinorDivisions;
        }

        public void setInnerMinorDivisions(int innerMinorDivisions) {
            this.innerMinorDivisions = innerMinorDivisions;
        }

        public int getInnerMajorDivisions() {
            return innerMajorDivisions;
        }

        public void setInnerMajorDivisions(int innerMajorDivisions) {
            this.innerMajorDivisions = innerMajorDivisions;
        }

        public plotPanel() {
            super(new BorderLayout());

        }

        public void buildPlot() {

            dataset1 = new DefaultValueDataset(0);
            dataset2 = new DefaultValueDataset(0);
            DialPlot dialplot = new DialPlot();
            dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
            dialplot.setDataset(0, dataset1);
            dialplot.setDataset(1, dataset2);
            StandardDialFrame standarddialframe = new StandardDialFrame();
            standarddialframe.setBackgroundPaint(Color.lightGray);
            standarddialframe.setForegroundPaint(Color.darkGray);
            dialplot.setDialFrame(standarddialframe);
            GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
            DialBackground dialbackground = new DialBackground(gradientpaint);
            dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
            dialplot.setBackground(dialbackground);
            DialTextAnnotation dialtextannotation = new DialTextAnnotation(variableName);
            dialtextannotation.setFont(new Font("Dialog", 1, 12));
            dialtextannotation.setRadius(0.7D);
            dialplot.addLayer(dialtextannotation);
            DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
            dialvalueindicator.setFont(new Font("Dialog", 0, 10));
            dialvalueindicator.setOutlinePaint(Color.darkGray);
            dialvalueindicator.setRadius(0.6D);
            dialvalueindicator.setAngle(-103D);
            dialplot.addLayer(dialvalueindicator);
            DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
            dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
            dialvalueindicator1.setOutlinePaint(Color.red);
            dialvalueindicator1.setRadius(0.6D);
            dialvalueindicator1.setAngle(-77D);
            dialplot.addLayer(dialvalueindicator1);
            StandardDialScale standarddialscale = new StandardDialScale(plotOuterBottonLimit, plotOuterTopLimit, -120D, -300D, outerMajorDivisions, outerMinorDivisions);
            standarddialscale.setTickRadius(0.88D);
            standarddialscale.setTickLabelOffset(0.15D);
            standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
            dialplot.addScale(0, standarddialscale);
            StandardDialScale standarddialscale1 = new StandardDialScale(plotInnerBottonLimit, plotInnerTopLimit, -120D, -300D, innerMajorDivisions, innerMinorDivisions);
            standarddialscale1.setTickRadius(0.5D);
            standarddialscale1.setTickLabelOffset(0.15D);
            standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 12));
            standarddialscale1.setMajorTickPaint(Color.red);
            standarddialscale1.setMinorTickPaint(Color.red);
            dialplot.addScale(1, standarddialscale1);
            dialplot.mapDatasetToScale(1, 1);
            StandardDialRange standarddialrange = new StandardDialRange(90D, 100D, Color.blue);
            standarddialrange.setScaleIndex(1);
            standarddialrange.setInnerRadius(0.6D);
            standarddialrange.setOuterRadius(0.6D);
            dialplot.addLayer(standarddialrange);
            org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1);
            pin.setRadius(0.55D);
            dialplot.addPointer(pin);
            org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
            dialplot.addPointer(pointer);
            DialCap dialcap = new DialCap();
            dialcap.setRadius(0.1D);
            dialplot.setCap(dialcap);
            JFreeChart jfreechart = new JFreeChart(dialplot);
            jfreechart.setTitle(plotTitle);
            add(new ChartPanel(jfreechart));
        }

        public void buildPlot2() {

        }

    }

    private void build() {
        PanamaHitek_DualDialPlot.plot.buildPlot();
    }

    /**
     * Devuelve un JPanel con el grafico en su interior
     *
     * @return Panel con grafico incrustado
     */
    public JPanel getPlotPanel() {
        build();
        return PanamaHitek_DualDialPlot.plot;
    }

    /**
     * Permite insertar le grafico generado en un JPanel dentro de cualquier
     * interfaz
     *
     * @param panel JPanel en el que se desea mostrar el grafico
     */
    public void insertToPanel(JPanel panel) {
        build();
        PanamaHitek_DualDialPlot.plot.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        panel.add(plot);
    }

    /**
     * Configura el valor que se quiere mostrar en el grafico en un momento dado
     *
     * @param value1 Valor de la aguja mayor
     * @param value2 Valor de la aguja menor
     */
    public void setValue(double value1, double value2) {
        PanamaHitek_DualDialPlot.plot.getDataset1().setValue(value1);
        PanamaHitek_DualDialPlot.plot.getDataset2().setValue(value2);
    }

    /**
     * Configura el titulo del grafico
     *
     * @param title Titulo del grafico
     */
    public void setPlotTitle(String title) {
        PanamaHitek_DualDialPlot.plot.setPlotTitle(title);
    }

    /**
     * Nombre de la variable a mostrar en el grafico
     *
     * @param variableName Nombre de la variable
     */
    public void setPlotVariableName(String variableName) {
        PanamaHitek_DualDialPlot.plot.setVariableName(variableName);
    }

    /**
     * Valores del limite superior de la grafica tipo reloj
     *
     * @param topValue1 Valor maximo de la grafica externa/izquierda
     * @param topValue2 Valor maximo de la grafica interna/derecha
     */
    public void setPlotTopLimitValues(int topValue1, int topValue2) {
        PanamaHitek_DualDialPlot.plot.setPlotOuterTopLimit(topValue1);
        PanamaHitek_DualDialPlot.plot.setPlotInnerTopLimit(topValue2);
    }

    /**
     * Valores del limite superior de la grafica tipo reloj
     *
     * @param topValue1 Valor maximo de la grafica externa/izquierda
     * @param topValue2 Valor maximo de la grafica interna/derecha
     */
    public void setPlotBottonLimitValues(int topValue1, int topValue2) {
        PanamaHitek_DualDialPlot.plot.setPlotOuterBottonLimit(topValue1);
        PanamaHitek_DualDialPlot.plot.setPlotInnerBottonLimit(topValue2);
    }

    /**
     * Cantidad de divisiones primarias en el grafico
     *
     * @param valuePlot1 Divisiones en el grafico exterior/izquierdo
     * @param valuePlot2 Divisiones en el grafico interior/derecho
     */
    public void setPlotMajorDivisions(int valuePlot1, int valuePlot2) {
        PanamaHitek_DualDialPlot.plot.setOuterMajorDivisions(valuePlot1);
        PanamaHitek_DualDialPlot.plot.setInnerMajorDivisions(valuePlot2);
    }

    /**
     * Cantidad de divisiones secundarias en el grafico
     *
     * @param valuePlot1 Divisiones en el grafico exterior/izquierdo
     * @param valuePlot2 Divisiones en el grafico interior/derecho
     */
    public void setPlotMinorDivisions(int valuePlot1, int valuePlot2) {
        PanamaHitek_DualDialPlot.plot.setInnerMinorDivisions(valuePlot1);
        PanamaHitek_DualDialPlot.plot.setOuterMinorDivisions(valuePlot2);
    }

    /**
     * Inicia el modo followUp de Arduino. En este modo se crea una conexion con
     * Arduino a traves del puerto serie. El Arduino debe estar configurado para
     * enviarle un dato numerico al programa en Java a traves de la funcion
     * Serial.println(). Se debe enviar un solo dato a la vez, siendo un valor
     * numerico sin caracteres especiales. Java lo recibira y lo traducira en
     * una posicion de la aguja en la grafica
     *
     * @param PORT_NAME Puerto COM en el que esta conectado Arduino
     * @param DATA_RATE Velocidad de transmision de datos. Debe ser la misma que
     * se configuro en el Arduino
     * @throws ArduinoException Posibles excepciones
     * @throws SerialPortException Posibles excepciones
     */
    public void createArduinoFollowUp(String PORT_NAME, int DATA_RATE) throws ArduinoException, SerialPortException {
        PanamaHitek_DualDialPlot.ino = new PanamaHitek_Arduino();
        multi = new PanamaHitek_MultiMessage(2, ino);
        SerialPortEventListener listener;
        listener = (SerialPortEvent serialPortEvent) -> {
            try {
                if (multi.dataReceptionCompleted()) {
                    setValue(Double.parseDouble(multi.getMessage(0)), Double.parseDouble(multi.getMessage(1)));
                    multi.flushBuffer();
                }

            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(PanamaHitek_DualDialPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        PanamaHitek_DualDialPlot.ino.arduinoRX(PORT_NAME, DATA_RATE, listener);
    }

    /**
     * Detiene el modo followUp de Arduino, cerrando la conexion con el puerto
     * serie
     *
     * @throws ArduinoException Posibles excepciones
     */
    public void stopArduinoFollowUp() throws ArduinoException {
        ino.killArduinoConnection();
    }

    /**
     * Constructor de la clase. Crea una grafica tipo reloj con nombre y titulos
     * por defecto
     *
     */
    public PanamaHitek_DualDialPlot() {
        PanamaHitek_DualDialPlot.plot = new plotPanel();
    }

    /**
     * Constructor de la clase. Le asigna el valor del parametro plotTitle a la
     * grafica tipo reloj
     *
     * @param plotTitle Titulo de la grafica
     */
    public PanamaHitek_DualDialPlot(String plotTitle) {
        PanamaHitek_DualDialPlot.plot = new plotPanel();
        PanamaHitek_DualDialPlot.plot.setPlotTitle(plotTitle);
    }

    /**
     * Constructor de la clase. Le asigna titulo y nombre a la grafica tipo
     * reloj
     *
     * @param plotTitle Titulo de la grafica
     * @param variableName Nombre de la grafica
     *
     */
    public PanamaHitek_DualDialPlot(String plotTitle, String variableName) {
        PanamaHitek_DualDialPlot.plot = new plotPanel();
        PanamaHitek_DualDialPlot.plot.setPlotTitle(plotTitle);
        PanamaHitek_DualDialPlot.plot.setVariableName(variableName);

    }

}
