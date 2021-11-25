package com.nikpappas.sketch.fractal;

public interface ComplexAny extends Comparable<ComplexAny> {
    ComplexAny square();
    double abs();
    ComplexAny add(ComplexAny c);

    double imaginary();
    double real();
}
