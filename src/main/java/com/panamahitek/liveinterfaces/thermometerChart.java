package com.panamahitek.liveinterfaces;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.data.general.DefaultValueDataset;

/**
 * Esta clase construye un grafico tipo reloj en la que se pueden graficar datos
 * recibidos desde Arduino en tiempo real
 *
 * @author Antony Garcia
 * @since 3.0.0
 */
public class thermometerChart extends JPanel {

    public final static int ROUND_DIAL_PLOT = 1;
    public final static int HORIZONTAL_DIAL_PLOT = 2;
    public final static int VERTICAL_DIAL_PLOT = 3;

    private static chartPanel chart;
    private static PanamaHitek_Arduino ino = null;

    private class chartPanel extends JPanel {

        //Dataset donde se guardan los datos
        private DefaultValueDataset dataset;
        //Titulo de la grafica
        private String plotTitle = "Default Title";
        //Nombre de la variable a graficar
        private String variableName = "Default Variable Name";
        private int plotBottonLimit = 0; //Limite inferior de la grafica
        private int plotTopLimit = 100; //Limite superior de la grafica
        private int redBottomLimit = 0; //Limite inferior del color rojo
        private int redTopLimit = 0; //Limite superior del color rojo
        private int yellowBottomLimit = 0;
        private int yellowTopLimit = 0;
        private int greenBottomLimit = 0;
        private int greenTopLimit = 0;
        private double greenSlice = 0.5; //Porcentaje del color verde
        private double yellowSlice = 0.3;
        private double redSlice = 0.2;
        private int unit = ThermometerPlot.UNITS_CELCIUS;

        public DefaultValueDataset getDataset() {
            return dataset;
        }

        public void setDataset(DefaultValueDataset dataset) {
            this.dataset = dataset;
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

        public int getPlotBottonLimit() {
            return plotBottonLimit;
        }

        public void setPlotBottonLimit(int plotBottonLimit) {
            this.plotBottonLimit = plotBottonLimit;
        }

        public int getPlotTopLimit() {
            return plotTopLimit;
        }

        public void setPlotTopLimit(int plotTopLimit) {
            this.plotTopLimit = plotTopLimit;
        }

        //Calculo de los limites de los colores segun el maximo y el minimo
        private void setColorLimits() {
            int minValue = plotBottonLimit;
            int maxValue = plotTopLimit;
            setGreenBottomLimit(minValue);
            setGreenTopLimit((int) (minValue + Math.abs(maxValue - minValue) * getGreenSlice()));
            setYellowBottomLimit(getGreenTopLimit());
            setYellowTopLimit((int) (getGreenTopLimit() + (Math.abs(maxValue - minValue) * getYellowSlice())));
            setRedBottomLimit(getYellowTopLimit());
            setRedTopLimit((int) (getYellowTopLimit() + (Math.abs(maxValue - minValue) * getRedSlice())));
        }

        public int getRedBottomLimit() {
            return redBottomLimit;
        }

        public void setRedBottomLimit(int redBottomLimit) {
            this.redBottomLimit = redBottomLimit;
        }

        public int getRedTopLimit() {
            return redTopLimit;
        }

        public void setRedTopLimit(int redTopLimit) {
            this.redTopLimit = redTopLimit;
        }

        public int getYellowBottomLimit() {
            return yellowBottomLimit;
        }

        public void setYellowBottomLimit(int yellowBottomLimit) {
            this.yellowBottomLimit = yellowBottomLimit;
        }

        public int getYellowTopLimit() {
            return yellowTopLimit;
        }

        public void setYellowTopLimit(int yellowTopLimit) {
            this.yellowTopLimit = yellowTopLimit;
        }

        public int getGreenBottomLimit() {
            return greenBottomLimit;
        }

        public void setGreenBottomLimit(int greenBottomLimit) {
            this.greenBottomLimit = greenBottomLimit;
        }

        public int getGreenTopLimit() {
            return greenTopLimit;
        }

        public void setGreenTopLimit(int greenTopLimit) {
            this.greenTopLimit = greenTopLimit;
        }

        public double getGreenSlice() {
            return greenSlice;
        }

        public void setGreenSlice(double greenSlice) {
            this.greenSlice = greenSlice;
        }

        public double getYellowSlice() {
            return yellowSlice;
        }

        public void setYellowSlice(double yellowSlice) {
            this.yellowSlice = yellowSlice;
        }

        public double getRedSlice() {
            return redSlice;
        }

        public void setRedSlice(double redSlice) {
            this.redSlice = redSlice;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public chartPanel() {
            super(new BorderLayout());

        }

        public void buildPlot1() {
            setColorLimits();
            dataset = new DefaultValueDataset(30);
            ThermometerPlot thermometerplot = new ThermometerPlot(dataset);
            thermometerplot.setRange(plotBottonLimit, plotTopLimit);
            thermometerplot.setUnits(ThermometerPlot.UNITS_CELCIUS);
            thermometerplot.setSubrange(0, greenBottomLimit, greenTopLimit);
            thermometerplot.setSubrangePaint(0, Color.green);
            thermometerplot.setSubrange(1, yellowBottomLimit, yellowTopLimit);
            thermometerplot.setSubrangePaint(1, Color.yellow);
            thermometerplot.setSubrange(2, redBottomLimit, redTopLimit);
            thermometerplot.setSubrangePaint(2, Color.red);
            JFreeChart jfreechart = new JFreeChart(plotTitle, thermometerplot);
            ChartUtilities.applyCurrentTheme(jfreechart);
            add(new ChartPanel(jfreechart));
        }

    }

    private void build() {
        thermometerChart.chart.buildPlot1();
    }

    /**
     * Devuelve un JPanel con el grafico en su interior
     *
     * @return Panel con grafico incrustado
     */
    public JPanel getPlotPanel() {
        build();
        return thermometerChart.chart;
    }

    /**
     * Valores limites del termometro
     *
     * @param minValue Valor minimo
     * @param maxValue Valor maximo
     */
    public void setChartLimitValues(int minValue, int maxValue) {
        thermometerChart.chart.setPlotBottonLimit(minValue);
        thermometerChart.chart.setPlotTopLimit(maxValue);
    }

    /**
     * Distribucion de los colores segun porcentaje
     *
     * @param firstArea Porcentaje del area verde
     * @param secondArea Porcentaje del area amarilla
     * @param thirdArea Porcentaje del area roja
     * @throws Exception Se dispara si la suma de los porcentajes no totaliza
     * 100
     */
    public void setColorDistribuition(int firstArea, int secondArea, int thirdArea) throws Exception {
        int total = firstArea + secondArea + thirdArea;
        if (total != 100) {
            throw new Exception("La suma de los 3 porcentajes debe totalizar 100%");
        }
        thermometerChart.chart.setGreenSlice(firstArea * 0.01);
        thermometerChart.chart.setYellowSlice(secondArea * 0.01);
        thermometerChart.chart.setRedSlice(thirdArea * 0.01);
    }

    /**
     * Permite insertar le grafico generado en un JPanel dentro de cualquier
     * interfaz
     *
     * @param panel JPanel en el que se desea mostrar el grafico
     */
    public void insertToPanel(JPanel panel) {
        build();
        thermometerChart.chart.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        panel.add(chart);
    }

    /**
     * Sets the units to be displayed in the thermometer. Use one of the
     * following constants:
     *
     * <ul>
     * <li>ThermometerPlot.UNITS_NONE : no units displayed.</li>
     * <li>ThermometerPlot.UNITS_FAHRENHEIT : units displayed in
     * Fahrenheit.</li>
     * <li>ThermometerPlot.UNITS_CELCIUS : units displayed in Celcius.</li>
     * <li>ThermometerPlot.UNITS_KELVIN : units displayed in Kelvin.</li>
     * </ul>
     *
     * @param unit the new unit type.
     *
     * @see #getUnits()
     */
    public void setThermometerUnit(int unit) {
        thermometerChart.chart.setUnit(unit);
    }

    /**
     * Configura el valor que se quiere mostrar en el grafico en un momento dado
     *
     * @param value Valor que se desea mostrar
     */
    public void setValue(double value) {
        thermometerChart.chart.getDataset().setValue(value);
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
        thermometerChart.ino = new PanamaHitek_Arduino();
        SerialPortEventListener listener;
        listener = (SerialPortEvent serialPortEvent) -> {
            try {
                if (ino.isMessageAvailable()) {
                    setValue(Double.parseDouble(ino.printMessage()));
                }
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(thermometerChart.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        thermometerChart.ino.arduinoRX(PORT_NAME, DATA_RATE, listener);
    }

    /**
     * Detiene el modo followUp de Arduino, cerrando la conexion con el puerto
     * serie
     *
     * @throws ArduinoException
     */
    public void stopArduinoFollowUp() throws ArduinoException {
        ino.killArduinoConnection();
    }

    /**
     * Constructor de la clase. Crea una grafica tipo reloj con nombre y titulos
     * por defecto
     *
     */
    public thermometerChart() {
        thermometerChart.chart = new chartPanel();
    }

    /**
     * Constructor de la clase. Le asigna el valor del parametro plotTitle a la
     * grafica tipo reloj
     *
     * @param plotTitle Titulo de la grafica
     *
     */
    public thermometerChart(String plotTitle) {
        thermometerChart.chart = new chartPanel();
        thermometerChart.chart.setPlotTitle(plotTitle);

    }

}
