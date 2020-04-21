package com.gmail.goyter012.Lab1_2;


import javax.swing.*;
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
        ArrayList<Double> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            list.add((double) (min + (int) (Math.random() * max)));
        }
        return list;
    }

    private ArrayList<Double> generateFreq() {
        ArrayList<Double> freq = new ArrayList<>();
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
        return new ArrayList<>(Arrays.asList(x));
    }

    private ArrayList<Double> generateRxx(ArrayList<Double> x, double mx) {
        Double[] rxx = new Double[(int) (nLarge / 2) - 1];
        Arrays.fill(rxx, 0.0);
        for (int i = 0; i < (int) (nLarge / 2) - 1; i++) {
            for (int j = 0; j < (int) (nLarge / 2) - 1; j++) {
                rxx[i] += ((x.get(j) - mx) * (x.get(i + j) - mx)) / (nLarge - 1);
            }
        }
        return new ArrayList<>(Arrays.asList(rxx));
    }

    private ArrayList<Double> generateRxy(ArrayList<Double> y, double mx, double my) {
        Double[] rxy = new Double[(int) (nLarge / 2) - 1];
        Arrays.fill(rxy, 0.0);
        for (int i = 0; i < (int) (nLarge / 2) - 1; i++) {
            for (int j = 0; j < (int) (nLarge / 2) - 1; j++) {
                rxy[i] += ((y.get(j) - mx) * (y.get(j + i) - my)) / (nLarge - 1);
            }
        }
        return new ArrayList<>(Arrays.asList(rxy));
    }

    private ArrayList<Double> generateYs(int size) {
        ArrayList<Double> ys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ys.add((double) i);
        }
        return ys;
    }

    private void drawGraphics(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> rxx, ArrayList<Double> rxxt, ArrayList<Double> rxy, ArrayList<Double> rxyt) {
        EventQueue.invokeLater(() -> {
            LineChartEx ex = new LineChartEx(x, y, rxx, rxxt, rxy, rxyt);
            ex.setVisible(true);
        });
    }


    public void runFirstLab() {
        ArrayList<Double> x = generateXt(generateList(0, 10), generateFreq(), generateList(0, 10));
        ArrayList<Double> y = generateXt(generateList(0, 10), generateFreq(), generateList(0, 10));
        double mx = generateMathematicalExpectation(x);
        double my = generateMathematicalExpectation(y);
        ArrayList<Double> rxx = generateRxx(x, mx);
        ArrayList<Double> rxy = generateRxy(y, mx, my);
        JOptionPane.showMessageDialog(null,
                "Mx: " + mx + "\n" +
                        "My: " + my + "\n" +
                        "Dx: " + generateDispersion(x, mx) + "\n" +
                        "Dy: " + generateDispersion(y, my) + "\n"
        );

        drawGraphics(x, generateYs(nLarge), rxx, generateYs((int) (nLarge / 2) - 1),rxy, generateYs((int) (nLarge / 2) - 1));

    }


}
