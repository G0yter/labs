package com.gmail.goyter012.RTS.Lab1_2_3_4;


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


    private ArrayList<Double> generateYs(int size) {
        ArrayList<Double> ys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ys.add((double) i);
        }
        return ys;
    }

    private ArrayList<Double> generateNList() {
        ArrayList<Double> list = new ArrayList<>(nLarge);
        for (int i = 0; i < nLarge; i++) {
            list.add((double) i);
        }
        return list;
    }

    private ArrayList<Complex> generateDFT(ArrayList<Double> xt) {
        ArrayList<Complex> complexNums = new ArrayList<>(nLarge);
        for (int k = 0; k < nLarge; k++) {
            complexNums.add(new Complex(0, 0));
            for (int n = 0; n < nLarge; n++) {
                double a = 2 * Math.PI * n * k / nLarge;
                complexNums.get(k).add(
                        new Complex(xt.get(n) * Math.cos(a),
                                -xt.get(n) * Math.sin(a))
                );
            }
        }
        return complexNums;
    }

    private ArrayList<Complex> generateFFT(ArrayList<Double> xt) {
        Complex[] complexes = new Complex[nLarge];
        for (int k = 0; k < nLarge / 2; k++) {
            Complex f1 = new Complex(0, 0);
            Complex f2 = new Complex(0, 0);
            for (int m = 0; m < nLarge / 2; m++) {
                double a = 2.0 * Math.PI * m * k / (nLarge / 2.0);
                f1.add(
                        new Complex(xt.get(2 * m) * Math.cos(a),
                                -xt.get(2 * m) * Math.sin(a))
                );
                f2.add(
                        new Complex(xt.get(2 * m + 1) * Math.cos(a),
                                -xt.get(2 * m + 1) * Math.sin(a))
                );
            }

            double a = 2.0 * Math.PI * k / nLarge;
            Complex factor = new Complex(Math.cos(a), -Math.sin(a));

            int p = nLarge / 2 - 1;
            if (p >= nLarge / 2) {
                p += nLarge / 2;
            }

            complexes[k] = Complex.sum(f1, Complex.mul(factor, f2));
            complexes[k + p + 1] = Complex.diff(f1, Complex.mul(factor, f2));
        }

        return new ArrayList<>(Arrays.asList(complexes));
    }


    private void drawGraphics(ArrayList<Double> x, ArrayList<Double> y, Paint color, String title, String graphName, String xLabel, String yLabel) {
        LineChartEx ex = new LineChartEx(x, y, color, title, graphName, xLabel, yLabel);
        ex.setVisible(true);

    }


    public void runFirstLab() {

        JOptionPane.showMessageDialog(null,
                "Just drag the window on the top in order to see all of the windows!"

        );
        ArrayList<Double> x = generateXt(generateList(0, 10), generateFreq(), generateList(0, 10));
        ArrayList<Complex> dft = generateDFT(x);
        ArrayList<Complex> fft = generateFFT(x);
        ArrayList<Double> modulesDFT = new ArrayList<>(nLarge);
        ArrayList<Double> modulesFFT = new ArrayList<>(nLarge);

        for (Complex complex : dft) {
            modulesDFT.add(complex.getModule());
        }
        for (Complex complex : fft) {
            modulesFFT.add(complex.getModule());
        }


        drawGraphics(x, generateYs(nLarge), Color.RED, "Random signals", "X(t)", "t", "x(t)");
        drawGraphics(generateNList(), modulesDFT, Color.GREEN, "Discrete Fourier transform", "DFT(N)", "n", "dft(n)");
        drawGraphics(generateNList(), modulesFFT, Color.BLUE, "Fast Fourier transform", "FFT(N)", "n", "fft(n)");


    }


}
