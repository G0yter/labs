package com.gmail.goyter012.RTS.Lab1_2_3_4;

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

    public LineChartEx(ArrayList<Double> x, ArrayList<Double> xt, Paint color, String title, String graphName, String xLabel, String yLabel) {
        initUI(x, xt, color, title, graphName, xLabel, yLabel);
    }

    private void initUI(ArrayList<Double> x, ArrayList<Double> xt, Paint color, String title, String graphName, String xLabel, String yLabel) {

        XYDataset dataset = createDataset(x, xt, graphName);
        JFreeChart chart = createChart(dataset, color, title, xLabel, yLabel);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(ArrayList<Double> x, ArrayList<Double> xt, String graphName) {

        XYSeries series = new XYSeries(graphName);
        for (Double value : x) {
            for (Double aDouble : xt) {
                series.add(value, aDouble);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);


        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, Paint color, String title, String xLabel, String yLabel) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, color);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));


        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title,
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }
}