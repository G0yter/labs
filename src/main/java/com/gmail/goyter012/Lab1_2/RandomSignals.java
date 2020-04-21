package com.gmail.goyter012.Lab1_2;

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

    private ArrayList<Double> listGenerator(int min, int max) {
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

    private double mExpectation(ArrayList<Double> line) {
        double expectation = 0;
        for (int i = 0; i < nLarge; i++) {
            expectation += line.get(i);
        }
        return expectation / nLarge;
    }

    private double dispersion(ArrayList<Double> line, double mExpectation) {
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
        System.out.println(Arrays.toString(x));
        return new ArrayList<Double>(Arrays.asList(x));
    }

    private void drawFunction() {

    }


    public void runFirstLab() {
        ArrayList<Double> x = generateXt(listGenerator(0, 5), generateFreq(), listGenerator(0, 5));
        ArrayList<Double> y = generateXt(listGenerator(0, 5), generateFreq(), listGenerator(0, 5));
        double mx = mExpectation(x);
        double my = mExpectation(y);
        System.out.println("mx: " + mx);
        System.out.println("my: " + my);
        System.out.println("dx: " + dispersion(x, mx));
        System.out.println("dy: " + dispersion(y, my));

        drawFunction();


    }


}
