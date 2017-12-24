// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 



import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class TimeSeriesDemo3 extends ApplicationFrame
{

	public TimeSeriesDemo3(String s)
	{
		super(s);
		XYDataset xydataset = createDataset();
		JFreeChart jfreechart = createChart(xydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(chartpanel);
	}

	private static XYDataset createDataset()
	{
		TimeSeries timeseries = new TimeSeries("Series 1");
		timeseries.add(new Month(1, 2002), 500.19999999999999D);
		timeseries.add(new Month(2, 2002), 694.10000000000002D);
		timeseries.add(new Month(3, 2002), 734.39999999999998D);
		timeseries.add(new Month(4, 2002), 453.19999999999999D);
		timeseries.add(new Month(5, 2002), 500.19999999999999D);
		timeseries.add(new Month(6, 2002), 345.60000000000002D);
		timeseries.add(new Month(7, 2002), 500.19999999999999D);
		timeseries.add(new Month(8, 2002), 694.10000000000002D);
		timeseries.add(new Month(9, 2002), 734.39999999999998D);
		timeseries.add(new Month(10, 2002), 453.19999999999999D);
		timeseries.add(new Month(11, 2002), 500.19999999999999D);
		timeseries.add(new Month(12, 2002), 345.60000000000002D);
		TimeSeries timeseries1 = new TimeSeries("Series 2");
		timeseries1.add(new Month(1, 2002), 234.09999999999999D);
		timeseries1.add(new Month(2, 2002), 623.70000000000005D);
		timeseries1.add(new Month(3, 2002), 642.5D);
		timeseries1.add(new Month(4, 2002), 651.39999999999998D);
		timeseries1.add(new Month(5, 2002), 643.5D);
		timeseries1.add(new Month(6, 2002), 785.60000000000002D);
		timeseries1.add(new Month(7, 2002), 234.09999999999999D);
		timeseries1.add(new Month(8, 2002), 623.70000000000005D);
		timeseries1.add(new Month(9, 2002), 642.5D);
		timeseries1.add(new Month(10, 2002), 651.39999999999998D);
		timeseries1.add(new Month(11, 2002), 643.5D);
		timeseries1.add(new Month(12, 2002), 785.60000000000002D);
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		timeseriescollection.addSeries(timeseries1);
		return timeseriescollection;
	}

	private static JFreeChart createChart(XYDataset xydataset)
	{
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Time Series Demo 3", "Time", "Value", xydataset, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1, new SimpleDateFormat("MMM-yyyy")));
		dateaxis.setVerticalTickLabels(true);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);
		xylineandshaperenderer.setSeriesFillPaint(0, Color.red);
		xylineandshaperenderer.setSeriesFillPaint(1, Color.white);
		xylineandshaperenderer.setUseFillPaint(true);
		xylineandshaperenderer.setLegendItemToolTipGenerator(new StandardXYSeriesLabelGenerator("Tooltip {0}"));
		return jfreechart;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		return new ChartPanel(jfreechart);
	}

	public static void main(String args[])
	{
		TimeSeriesDemo3 timeseriesdemo3 = new TimeSeriesDemo3("Time Series Demo 3");
		timeseriesdemo3.pack();
		RefineryUtilities.centerFrameOnScreen(timeseriesdemo3);
		timeseriesdemo3.setVisible(true);
	}
}
