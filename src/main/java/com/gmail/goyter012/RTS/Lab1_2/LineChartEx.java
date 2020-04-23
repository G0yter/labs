package com.gmail.goyter012.RTS.Lab1_2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LineChartEx extends JFrame {

    public LineChartEx(ArrayList<Double> x, ArrayList<Double> xt, ArrayList<Double> rxx, ArrayList<Double> rxxt, ArrayList<Double> rxy, ArrayList<Double> rxyt) {
        initUI(x, xt, rxx, rxxt, rxy, rxyt);
    }

    private void initUI(ArrayList<Double> x, ArrayList<Double> xt, ArrayList<Double> rxx, ArrayList<Double> rxxt, ArrayList<Double> rxy, ArrayList<Double> rxyt) {

        XYDataset dataset = createDataset(x, xt, rxx, rxxt, rxy, rxyt);
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Random signals");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(ArrayList<Double> x, ArrayList<Double> xt, ArrayList<Double> rxx, ArrayList<Double> rxxt, ArrayList<Double> rxy, ArrayList<Double> rxyt) {

        XYSeries series = new XYSeries("X(t)");
        for (Double value : x) {
            for (Double aDouble : xt) {
                series.add(value, aDouble);
            }
        }
        XYSeries series1 = new XYSeries("Rxx(t)");
        for (Double value : rxx) {
            for (Double aDouble : rxxt) {
                series1.add(value, aDouble);
            }
        }
        XYSeries series2 = new XYSeries("Rxy(t)");
        for (Double value : rxy) {
            for (Double aDouble : rxyt) {
                series2.add(value, aDouble);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(series1);
        dataset.addSeries(series2);


        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                "t",
                "x(t)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        renderer.setSeriesPaint(1, Color.BLUE);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Random signals",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }
}