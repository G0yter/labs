package com.gmail.goyter012.RTS.Lab1_2_3_4;

public class Complex {
    private double real;
    private double imaginary;

    Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    double getModule() {
        return Math.hypot(real, imaginary);
    }

    void add(Complex c) {
        this.real += c.real;
        this.imaginary += c.imaginary;
    }

    static Complex diff(Complex a, Complex b) {
        double real = a.real - b.real;
        double imaginary = a.imaginary - b.imaginary;
        return new Complex(real, imaginary);
    }

    static Complex mul(Complex a, Complex b) {
        double real = a.real * b.real - a.imaginary * b.imaginary;
        double imaginary = a.real * b.imaginary + a.imaginary * b.real;
        return new Complex(real, imaginary);
    }

    static Complex sum(Complex a, Complex b) {
        double real = a.real + b.real;
        double imaginary = a.imaginary + b.imaginary;
        return new Complex(real, imaginary);
    }
}
