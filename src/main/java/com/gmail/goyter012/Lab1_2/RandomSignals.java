package com.gmail.goyter012.Lab1_2;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RandomSignals {
    private int n;
    private int nLarge;
    private int omega;

    public RandomSignals(int n, int nLarge, int omega) {
        this.n = n;
        this.nLarge = nLarge;
        this.omega = omega;
    }

    private ArrayList<Double> generateList(int min, int max) {
        ArrayList<Double> list = new ArrayList<Double>(n);
        for (int i = 0; i < n; i++) {
            list.add((double) (min + (int) (Math.random() * max)));
        }
        return list;
    }

    private ArrayList<Double> generateFreq() {
        ArrayList<Double> freq = new ArrayList<Double>();
        int step = omega / n;
        for (int i = 0; i < n; i++) {
            freq.add((double) (omega - step * i));
        }
        return freq;
    }

    private double generateMathematicalExpectation(ArrayList<Double> line) {
        double expectation = 0;
        for (int i = 0; i < nLarge; i++) {
            expectation += line.get(i);
        }
        return expectation / nLarge;
    }

    private double generateDispersion(ArrayList<Double> line, double mExpectation) {
        double dispersion = 0;
        for (int i = 0; i < nLarge; i++) {
            dispersion += Math.pow(line.get(i) - mExpectation, 2);
        }
        return dispersion / (nLarge - 1);
    }

    private ArrayList<Double> generateXt(ArrayList<Double> a, ArrayList<Double> freq, ArrayList<Double> alpha) {
        Double[] x = new Double[nLarge];
        Arrays.fill(x, 0.0);
        for (int i = 0; i < nLarge; i++) {
            for (int j = 0; j < n; j++) {
                x[i] += a.get(j) * Math.sin(freq.get(j) * i + alpha.get(j));
            }
        }
        return new ArrayList<Double>(Arrays.asList(x));
    }

    private void drawGraphics(ArrayList<Double> x, ArrayList<Double> y) {
        EventQueue.invokeLater(() -> {
            LineChartEx ex = new LineChartEx(x, y);
            ex.setVisible(true);
        });
    }


    public void runFirstLab() {
        ArrayList<Double> x = generateXt(generateList(0, 10), generateFreq(), generateList(0, 10));
        ArrayList<Double> y = generateXt(generateList(0, 10), generateFreq(), generateList(0, 10));
        double mx = generateMathematicalExpectation(x);
        double my = generateMathematicalExpectation(y);
        System.out.println("mx: " + mx);
        System.out.println("my: " + my);
        System.out.println("dx: " + generateDispersion(x, mx));
        System.out.println("dy: " + generateDispersion(y, my));

        drawGraphics(x, y);


    }


}
