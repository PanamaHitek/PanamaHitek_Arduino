// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
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
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

public class singleDialPlot extends JPanel {

    private static plotPanel plot;
    private static PanamaHitek_Arduino ino = null;

    private class plotPanel extends JPanel {

        private DefaultValueDataset dataset;
        private String plotTitle = "Default Title";
        private String variableName = "Default Variable Name";
        private int plotBottonLimit = 0;
        private int plotTopLimit = 100;
        private int minorDivisions = 4;
        private int majorDivisions = 10;
        private int redBottomLimit = 0;
        private int redTopLimit = 0;
        private int yellowBottomLimit = 0;
        private int yellowTopLimit = 0;
        private int greenBottomLimit = 0;
        private int greenTopLimit = 0;
        private double greenSlice = 0.5;
        private double yellowSlice = 0.3;
        private double redSlice = 0.2;

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

        public int getMinorDivisions() {
            return minorDivisions;
        }

        public void setMinorDivisions(int minorDivisions) {
            this.minorDivisions = minorDivisions;
        }

        public int getMajorDivisions() {
            return majorDivisions;
        }

        public void setMajorDivisions(int majorDivisions) {
            this.majorDivisions = majorDivisions;
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

        public JFreeChart createStandardDialChart(String s, String s1, ValueDataset valuedataset, double d, double d1, double d2, int i) {
            DialPlot dialplot = new DialPlot();
            dialplot.setDataset(valuedataset);
            dialplot.setDialFrame(new StandardDialFrame());
            dialplot.setBackground(new DialBackground());
            DialTextAnnotation dialtextannotation = new DialTextAnnotation(s1);
            dialtextannotation.setFont(new Font("Dialog", 1, 14));
            dialtextannotation.setRadius(0.7D);
            dialplot.addLayer(dialtextannotation);
            DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
            dialplot.addLayer(dialvalueindicator);
            StandardDialScale standarddialscale = new StandardDialScale(d, d1, -120D, -300D, 10D, 4);
            standarddialscale.setMajorTickIncrement(d2);
            standarddialscale.setMinorTickCount(i);
            standarddialscale.setTickRadius(0.88D);
            standarddialscale.setTickLabelOffset(0.15D);
            standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
            dialplot.addScale(0, standarddialscale);
            dialplot.addPointer(new org.jfree.chart.plot.dial.DialPointer.Pin());
            DialCap dialcap = new DialCap();
            dialplot.setCap(dialcap);
            return new JFreeChart(s, dialplot);
        }

        public plotPanel() {
            super(new BorderLayout());

        }

        public void buildPlot() {
            setColorLimits();
            dataset = new DefaultValueDataset(10D);
            JFreeChart jfreechart = createStandardDialChart(plotTitle, variableName, dataset, plotBottonLimit, plotTopLimit, majorDivisions, minorDivisions);
            DialPlot dialplot = (DialPlot) jfreechart.getPlot();
            StandardDialRange standarddialrange = new StandardDialRange(redBottomLimit, redTopLimit, Color.red);
            standarddialrange.setInnerRadius(0.522D);
            standarddialrange.setOuterRadius(0.554D);
            dialplot.addLayer(standarddialrange);
            StandardDialRange standarddialrange1 = new StandardDialRange(yellowBottomLimit, yellowTopLimit, Color.orange);
            standarddialrange1.setInnerRadius(0.522D);
            standarddialrange1.setOuterRadius(0.554D);
            dialplot.addLayer(standarddialrange1);
            StandardDialRange standarddialrange2 = new StandardDialRange(greenBottomLimit, greenTopLimit, Color.green);
            standarddialrange2.setInnerRadius(0.522D);
            standarddialrange2.setOuterRadius(0.554D);
            dialplot.addLayer(standarddialrange2);
            GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
            DialBackground dialbackground = new DialBackground(gradientpaint);
            dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
            dialplot.setBackground(dialbackground);
            dialplot.removePointer(0);
            org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();
            dialplot.addPointer(pointer);
            ChartPanel chartpanel = new ChartPanel(jfreechart);
            chartpanel.setPreferredSize(new Dimension(400, 400));
            add(chartpanel);
        }
    }

    public JPanel getPlotPanel() {
        singleDialPlot.plot.buildPlot();
        return singleDialPlot.plot;
    }

    public void insertToPanel(JPanel panel) {
        singleDialPlot.plot.buildPlot();
        singleDialPlot.plot.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        panel.add(plot);
    }

    public void setValue(double num) {
        singleDialPlot.plot.getDataset().setValue(num);
    }

    public void setPlotTitle(String title) {
        singleDialPlot.plot.setPlotTitle(title);
    }

    public void setPlotLimitValues(int minValue, int maxValue) {
        singleDialPlot.plot.setPlotBottonLimit(minValue);
        singleDialPlot.plot.setPlotTopLimit(maxValue);
    }

    public void setColorDistribuition(int firstArea, int secondArea, int thirdArea) throws Exception {
        int total = firstArea + secondArea + thirdArea;
        if (total != 100) {
            throw new Exception("La suma de los 3 porcentajes debe totalizar 100%");
        }
        singleDialPlot.plot.setGreenSlice(firstArea * 0.01);
        singleDialPlot.plot.setYellowSlice(secondArea * 0.01);
        singleDialPlot.plot.setRedSlice(thirdArea * 0.01);
    }

    public void createArduinoFollowUp(String PORT_NAME, int DATA_RATE) throws ArduinoException, SerialPortException {
        singleDialPlot.ino = new PanamaHitek_Arduino();
        SerialPortEventListener listener;
        listener = (SerialPortEvent serialPortEvent) -> {
            try {
                if (ino.isMessageAvailable()) {
                    setValue(Double.parseDouble(ino.printMessage()));
                }
            } catch (SerialPortException | ArduinoException ex) {
                Logger.getLogger(singleDialPlot.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        singleDialPlot.ino.arduinoRX(PORT_NAME, DATA_RATE, listener);
    }

    public void stopArduinoFollowUp() throws ArduinoException {
        ino.killArduinoConnection();
    }

    public singleDialPlot() {
        singleDialPlot.plot = new plotPanel();
    }

    public singleDialPlot(String plotTitle) {
        singleDialPlot.plot = new plotPanel();
        singleDialPlot.plot.setPlotTitle(plotTitle);
    }

    public singleDialPlot(String plotTitle, String variableName) {
        singleDialPlot.plot = new plotPanel();
        singleDialPlot.plot.setPlotTitle(plotTitle);
        singleDialPlot.plot.setVariableName(variableName);
    }

}
