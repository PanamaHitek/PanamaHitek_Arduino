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

import com.panamahitek.PanamaHitek_Arduino;
import com.panamahitek.PanamaHitek_DataBuffer;
import com.panamahitek.PanamaHitek_MultiMessage;
import com.panamahitek.events.DataInsertionEvent;
import com.panamahitek.events.DataInsertionListener;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Stroke;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Antony García González, de Proyecto Panama Hitek. Visita
 * http://panamahitek.com
 * @since 3.0.0
 */
public class PanamaHitek_TimeLineChart extends JPanel {

    private PanamaHitek_TimeLineChart.chartPanel chart;
    private PanamaHitek_Arduino ino = null;
    private PanamaHitek_MultiMessage multi;
    private PanamaHitek_DataBuffer buffer;
    private List<Integer> indexList;

    private DataInsertionListener dataInsertionListener = new DataInsertionListener() {
        @Override
        public void onDataInsertion(DataInsertionEvent ev) {
            for (int i = 0; i < indexList.size(); i++) {
                int index = indexList.get(i);

                chart.getDataSeriesList().get(index).add(new Millisecond(
                        Integer.parseInt(new SimpleDateFormat("SSS").format(new Date())),
                        Integer.parseInt(new SimpleDateFormat("ss").format(new Date())),
                        Integer.parseInt(new SimpleDateFormat("mm").format(new Date())),
                        Integer.parseInt(new SimpleDateFormat("hh").format(new Date())),
                        Integer.parseInt(new SimpleDateFormat("dd").format(new Date())),
                        Integer.parseInt(new SimpleDateFormat("MM").format(new Date())),
                        Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()))),
                        Double.parseDouble(String.valueOf(buffer.getMainBuffer().get(index).get(buffer.getRowCount() - 1))));
            }
        }
    };

    private class chartPanel extends JPanel {

        //Titulo de la grafica
        private String chartTitle = "Default Title";
        private String xAxisName = "Default xAxis";
        private String yAxisName = "Default yAxis";
        private List<TimeSeries> dataSeries;
        private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        private XYLineAndShapeRenderer xylineandshaperenderer;
        private JFreeChart jfreechart;
        private XYPlot xyplot;
        private DateAxis dateaxis;
        private int itemCount = 0;
        private XYDataset dset;
        private List<Color> colorList;
        private List<Stroke> strokeList;
        private int dateIndex = -1;
        private Color backgroundColor = Color.WHITE;
        private Color gridLineColor = Color.GRAY;
        private boolean gridLines = true;
        private boolean linesMarks = true;

        public chartPanel() {
            super(new BorderLayout());
            dataSeries = new ArrayList<>();
            colorList = new ArrayList<>();
            strokeList = new ArrayList<>();
        }

        private void createChart() {

            TimeSeriesCollection seriesCollector = new TimeSeriesCollection();
            if (itemCount > 0) {
                dataSeries.forEach(i -> {
                    if (i != null) {
                        seriesCollector.addSeries(i);
                        i.setMaximumItemCount(itemCount);
                    }
                });
            }
            jfreechart = ChartFactory.createTimeSeriesChart(chartTitle, xAxisName, yAxisName, seriesCollector, true, true, false);
            xyplot = (XYPlot) jfreechart.getPlot();
            xyplot.setDomainPannable(true);
            xyplot.setRangePannable(false);
            xyplot.setDomainCrosshairVisible(true);
            xyplot.setRangeCrosshairVisible(true);
            xyplot.setBackgroundPaint(backgroundColor);
            xyplot.setRangeGridlinePaint(gridLineColor);
            xyplot.setRangeGridlinesVisible(gridLines);

            org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
            if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
                XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyitemrenderer;
                xylineandshaperenderer.setBaseShapesVisible(false);
            }
            dateaxis = (DateAxis) xyplot.getDomainAxis();
            dateaxis.setDateFormatOverride(dateFormat);

            //Marcas en los puntos-lineas
            if (linesMarks) {
                xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
                xylineandshaperenderer.setBaseShapesVisible(true);
                xylineandshaperenderer.setLegendItemToolTipGenerator(new StandardXYSeriesLabelGenerator("Tooltip {0}"));
            }

            XYItemRenderer xyir = this.jfreechart.getXYPlot().getRenderer();
            int colorIndex = 0;
            for (int i = 0; i < colorList.size(); i++) {
                if (colorList != null) {
                    if (i == dateIndex) {
                        colorIndex--;
                    } else {
                        xyir.setSeriesPaint(colorIndex, colorList.get(i));
                        xyir.setSeriesStroke(colorIndex, strokeList.get(i));
                    }
                }
                colorIndex++;
            }
            add(new ChartPanel(jfreechart));

        }

        public void buildChart() {
            createChart();
        }

        public void addDataSeries(TimeSeries timeSerie) {
            dataSeries.add(timeSerie);
        }

        public List<TimeSeries> getDataSeriesList() {
            return dataSeries;
        }

        public String getChartTitle() {
            return chartTitle;
        }

        public void setChartTitle(String chartTitle) {
            this.chartTitle = chartTitle;
        }

        public String getXAxisName() {
            return xAxisName;
        }

        public void setXAxisName(String xAxisName) {
            this.xAxisName = xAxisName;
        }

        public String getYAxisName() {
            return yAxisName;
        }

        public void setYAxisName(String yAxisName) {
            this.yAxisName = yAxisName;
        }

        public List<TimeSeries> getDataSeries() {
            return dataSeries;
        }

        public void setDataSeries(List<TimeSeries> dataSeries) {
            this.dataSeries = dataSeries;
        }

        public SimpleDateFormat getDateFormat() {
            return dateFormat;
        }

        public void setDateFormat(SimpleDateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        public XYLineAndShapeRenderer getXylineandshaperenderer() {
            return xylineandshaperenderer;
        }

        public void setXylineandshaperenderer(XYLineAndShapeRenderer xylineandshaperenderer) {
            this.xylineandshaperenderer = xylineandshaperenderer;
        }

        public JFreeChart getJfreechart() {
            return jfreechart;
        }

        public void setJfreechart(JFreeChart jfreechart) {
            this.jfreechart = jfreechart;
        }

        public XYPlot getXyplot() {
            return xyplot;
        }

        public void setXyplot(XYPlot xyplot) {
            this.xyplot = xyplot;
        }

        public DateAxis getDateaxis() {
            return dateaxis;
        }

        public void setDateaxis(DateAxis dateaxis) {
            this.dateaxis = dateaxis;
        }

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        public void setSeriesColor(Color color) {
            colorList.add(color);
        }

        public void setSeriesColor(int index, Color color) {
            colorList.set(index, color);
        }

        public void setDateIndex(int index) {
            this.dateIndex = index;
        }

        public void setSeriesThickness(int seriesIndex, int thickness) {
            this.strokeList.set(seriesIndex, new BasicStroke(thickness));
        }

        public void setSeriesThickness(int thickness) {
            this.strokeList.add(new BasicStroke(thickness));
        }

        public void setSeriesStroke(int seriesIndex, Stroke stroke) {
            this.strokeList.set(seriesIndex, stroke);
        }

        public void setSeriesStroke(Stroke stroke) {
            this.strokeList.add(stroke);
        }

        public void setBackgroundColor(Color color) {
            this.backgroundColor = color;
        }

        public void setGridLinesColor(Color color) {
            this.gridLineColor = color;
        }

        public void setGridLinesVisible(boolean visible) {
            this.gridLines = visible;
        }

        public void setLinesMarks(boolean visible) {
            this.linesMarks = visible;
        }
    }

    /**
     * Construye la grafica
     */
    private void build() {
        this.chart.buildChart();
    }

    /**
     * Devuelve un JPanel con el grafico en su interior
     *
     * @return Panel con grafico incrustado
     */
    public JPanel getChartPanel() {
        build();
        return this.chart;
    }

    /**
     * Permite insertar le grafico generado en un JPanel dentro de cualquier
     * interfaz
     *
     * @param panel JPanel en el que se desea mostrar el grafico
     */
    public void insertToPanel(JPanel panel) {
        build();
        this.chart.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        panel.add(chart);
    }

    /**
     * Constuctor de la clase
     */
    public PanamaHitek_TimeLineChart() {
        this.chart = new chartPanel();
    }

    /**
     * Metodo que permite establecer el buffer de datos (una instancia de la
     * clase PanamaHitek_DataBuffer). El metodo buscara en el buffer y graficara
     * los datos que se encuentren almacenados en el mismo
     *
     * @param buffer Instancia de la clase PanamaHitek_DataBuffer
     * @throws Exception Se pueden producir excepciones si hay datos no
     * graficables en el buffer. El buffer puede tener al menos una columna con
     * datos tipo Date, pero no permite datos no numericos (String, char,
     * boolean...)
     */
    public void setDataBuffer(PanamaHitek_DataBuffer buffer) throws Exception {
        this.buffer = buffer;
        indexList = new ArrayList<>();
        boolean dateFound = false;

        for (int i = 0; i < buffer.getClassList().size(); i++) {
            if (isDate(buffer.getClassList().get(i))) {
                this.chart.setDateIndex(i);
                if (!dateFound) {
                    dateFound = true;
                    chart.addDataSeries(null);
                } else {
                    throw new Exception("Objeto PanamaHitek_DataBuffer no válido."
                            + " Contiene dos columnas con objetos de la clase Date. "
                            + "Si desea utilizar este objeto, especifique las columnas"
                            + " que desea graficar.");
                }
            } else {
                if (isNumeric(buffer.getClassList().get(i))) {
                    indexList.add(i);
                    chart.addDataSeries(new TimeSeries(buffer.getVariableList().get(i)));
                } else {
                    throw new Exception("Objeto PanamaHitek_DataBuffer no válido."
                            + " La columna \"" + i + "\" contiene columnas con datos no "
                            + "numéricos, los cuales no pueden ser graficados");
                }
            }
            chart.setSeriesColor(null);
            chart.setSeriesStroke(null);
        }

        buffer.addEventListener(dataInsertionListener);
    }

    /**
     * Metodo que permite establecer el buffer de datos (una instancia de la
     * clase PanamaHitek_DataBuffer). El metodo permite graficar una sola serie
     * de datos
     *
     * @param buffer Instancia de la clase PanamaHitek_DataBuffer
     * @param index Indice del buffer que se desea graficar
     * @throws Exception Se pueden producir excepciones si hay datos no
     * graficables en el indice del buffer. No es posible graficar datos tipo
     * String, boolean, char...
     */
    public void setDataBuffer(PanamaHitek_DataBuffer buffer, int index) throws Exception {
        if (index < 0) {
            throw new Exception("El parametro index debe ser igual o mayor a cero");
        } else if (index > buffer.getColumnCount()) {
            throw new Exception("El parametro index no debe ser mayor a la cantidad de columnas declaradas en el buffer");
        }
        if (!isNumeric(buffer.getClassList().get(index))) {
            throw new Exception("La columna especificada no corresponde a un dato numerico");
        }
        this.buffer = buffer;
        indexList = new ArrayList<>();
        indexList.add(index);
        chart.addDataSeries(new TimeSeries(buffer.getVariableList().get(index)));
        chart.setSeriesColor(null);
        chart.setSeriesStroke(null);
        buffer.addEventListener(dataInsertionListener);
    }

    /**
     * Metodo que permite establecer el buffer de datos (una instancia de la
     * clase PanamaHitek_DataBuffer). El metodo permite graficar una sola serie
     * de datos
     *
     * @param buffer Instancia de la clase PanamaHitek_DataBuffer
     * @param bufferIndexes Arreglo con los indices del buffer que se desean
     * graficar
     * @throws Exception Se pueden producir excepciones si hay datos no
     * graficables establecidos en el arreglo de los indices. No es posible
     * graficar datos tipo String, boolean, char...
     */
    public void setDataBuffer(PanamaHitek_DataBuffer buffer, Integer[] bufferIndexes) throws Exception {
        this.buffer = buffer;
        indexList = new ArrayList<>();
        indexList.addAll(Arrays.asList(bufferIndexes));
        for (int i = 0; i < indexList.size(); i++) {
            int index = indexList.get(i);

            if (index < 0) {
                throw new Exception("El parametro index debe ser igual o mayor a cero");
            } else if (index > buffer.getColumnCount()) {
                throw new Exception("El parametro index no debe ser mayor a la cantidad de columnas declaradas en el buffer");
            }
            if (!isNumeric(buffer.getClassList().get(index))) {
                throw new Exception("La columna especificada no corresponde a un dato numerico");
            }

            chart.setSeriesColor(null);
            chart.setSeriesStroke(null);
            chart.addDataSeries(new TimeSeries(buffer.getVariableList().get(indexList.get(i))));
        }
        buffer.addEventListener(dataInsertionListener);
    }

    /**
     * Establece el formato de las marcas en el eje horizontal (eje de tiempo)
     *
     * @param format Se debe especificar el formato en el que se desea
     * representar el tiempo
     *
     * @see SimpleDateFormat
     */
    public void setTimeAxisFormat(SimpleDateFormat format) {
        this.chart.setDateFormat(format);
    }

    /**
     * Orientacion de las etiquetas del eje horizontal.
     *
     * @param flag TRUE para vertical, FALSE para horizontal
     */
    public void setDateAxisVerticalLabel(boolean flag) {
        this.chart.getDateaxis().setVerticalTickLabels(flag);
    }

    /**
     * Brinda acceso directo al objeto JFreeChart encargado de construir la
     * gráfica
     *
     * @return Objeto JFreeChart con todos los elementos que forman el gráfico
     * @see JFreeChart
     */
    public JFreeChart getChartObject() {
        return this.chart.getJfreechart();
    }

    /**
     * Permite establecer la mayor cantidad de datos a ser graficados en un
     * momento dado
     *
     * @param itemCount Cantidad de datos
     * @throws Exception Se puede producir una excepcion en caso de que se
     * establezca un entero negativo, lo cual no es posible
     */
    public void setMaximumItemCount(int itemCount) throws Exception {
        if (itemCount < 0) {
            throw new Exception("El parametro itemCount debe ser >= 0");
        } else {
            this.chart.setItemCount(itemCount);
        }
    }

    /**
     * Permite establecer el color de las lineas en la grafica
     *
     * @param serieIndex El indice de la serie de datos
     * @param color El color de la linea
     * @throws Exception Se puede producir una excepcion si el indice
     * establecido es superior a la cantidad de series declaradas, o si se
     * establece un indice negativo
     */
    public void setLineColor(int serieIndex, Color color) throws Exception {

        if ((serieIndex > (this.chart.getDataSeriesList().size())) || (serieIndex < 0)) {
            throw new Exception("El parametro seriesIndex no puede ser mayor a la cantidad de series de datos declarados");
        }
        this.chart.setSeriesColor(serieIndex, color);
    }

    /**
     * Permite establecer el color de las lineas en la grafica
     *
     * @param serieIndex El indice de la serie de datos
     * @param thickness El grosor de la linea (1 es la linea mas delgada)
     * @throws Exception Se puede producir una excepcion si el indice
     * establecido es superior a la cantidad de series declaradas, o si se
     * establece un indice negativo
     */
    public void setLineThickness(int serieIndex, int thickness) throws Exception {
        if (serieIndex > (this.chart.getDataSeriesList().size() - 1) || (serieIndex < 0)) {
            throw new Exception("El parametro seriesIndex no puede ser mayor a la cantidad de series de datos declaradas");
        }
        this.chart.setSeriesThickness(serieIndex, thickness);
    }

    /**
     * Permite establecer el color de las lineas en la grafica
     *
     * @param serieIndex El indice de la serie de datos
     * @param stroke Stroke para las lineas de la grafica
     * @throws Exception Se puede producir una excepcion si el indice
     * establecido es superior a la cantidad de series declaradas, o si se
     * establece un indice negativo
     *
     * @see Stroke
     */
    public void setLineStroke(int serieIndex, Stroke stroke) throws Exception {
        if (serieIndex > (this.chart.getDataSeriesList().size() - 1) || (serieIndex < 0)) {
            throw new Exception("El parametro seriesIndex no puede ser mayor a la cantidad de series de datos declaradas");
        }
        chart.setSeriesStroke(serieIndex, stroke);
    }

    /**
     * Establece el color de fondo de la grafica
     *
     * @param color Color de fondo
     */
    public void setBackgroundColor(Color color) {
        this.chart.setBackgroundColor(color);
    }

    /**
     * Establece el color de las lineas del mallado de la grafica
     *
     * @param color Color de las lineas
     */
    public void setGridLinesColor(Color color) {
        this.chart.setGridLinesColor(color);
    }

    /**
     * Activa o desactiva la visibilidad de las lineas del mallado de la grafica
     *
     * @param visible TRUE para activar, FALSE para desactivar
     */
    public void setGridLinesVisible(boolean visible) {
        this.chart.setGridLinesVisible(visible);
    }

    /**
     * Permite establecer los límites del eje horizontal
     *
     * @param lower Límite inferior
     * @param upper Límite superior
     * 
     *@since 3.0.3
     */
    public void setHorizontalAxisRange(double lower, double upper) {
        this.chart.getXyplot().getDomainAxis().setRange(lower, upper);
    }

    /**
     * Permite establecer los límites del eje vertical
     *
     * @param lower Límite inferior
     * @param upper Límite superior
     * 
     * @since 3.0.3
     */
    public void setVerticalAxisRange(double lower, double upper) {
        this.chart.getXyplot().getRangeAxis().setRange(lower, upper);
    }

    /**
     * Activa o desactiva la visibilidad de las marcas en los puntos de la
     * grafica
     *
     * @param visible TRUE para activar, FALSE para desactivar
     */
    public void setLinesMarksVisible(boolean visible) {
        this.chart.setLinesMarks(visible);
    }

    public void setChartTitle(String title) {
        this.chart.setChartTitle(title);
    }

    public void setAxisTitle(String titleAxisX, String titleAxisY) {
        this.chart.setXAxisName(titleAxisX);
        this.chart.setYAxisName(titleAxisY);
    }

    /**
     * Permite determinar si un objeto es del tipo String o no
     *
     * @param inputClass Objeto a verificar
     * @return Si el dato es String o no
     */
    private boolean isNumeric(Object inputClass) {
        return (inputClass instanceof Integer)
                || (inputClass.equals(Integer.class))
                || (inputClass instanceof Double)
                || (inputClass.equals(Double.class))
                || (inputClass instanceof Float)
                || (inputClass.equals(Float.class))
                || (inputClass instanceof Short)
                || (inputClass.equals(Short.class))
                || (inputClass instanceof Long)
                || (inputClass.equals(Long.class));
    }

    /**
     * Permite determinar si un objeto es del tipo numerico o no
     *
     * @param inputClass Objeto
     * @return Si el dato es numerico o no
     */
    private boolean isDate(Object inputClass) {
        return (inputClass instanceof Date)
                || (inputClass.equals(Date.class));
    }

}
