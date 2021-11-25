package com.nikpappas.sketch.fractal;

import java.util.function.BiFunction;

@FunctionalInterface
interface MandelbrotFunction extends BiFunction<ComplexAny, ComplexAny, ComplexAny> {
}

public class MandelbrotSet {
    private final int maxIterations;

    private static final int DEFAULT_ITERATIONS = 30000;
    private static final MandelbrotFunction SQUARE = (cur, c) -> (cur.square()).add(c);
    private static final MandelbrotFunction CUBE = (cur, c) -> ((cur.square()).multiply(cur)).add(c);
    private final MandelbrotFunction function;

    public MandelbrotSet() {
        this(DEFAULT_ITERATIONS);
    }

    public MandelbrotSet(int maxIterations) {
        this(maxIterations, SQUARE);
    }

    private MandelbrotSet(int maxIterations, MandelbrotFunction function) {
        this.maxIterations = maxIterations;
        this.function = function;
    }

    public boolean contains(ComplexAny c) {
        return isMandelbrot(c);
    }

    private boolean isMandelbrot(ComplexAny c) {
        double limit = 2;
        ComplexAny cur = c;
        for (int i = 0; i < maxIterations; i++) {
//            System.out.println(i);
            cur = function.apply(cur, c);
//            System.out.println(cur);
            if (cur.abs() > (limit)) {
                return false;
            }
        }
        return true;
    }

    public static MandelbrotSet ofSquare() {
        return new MandelbrotSet(DEFAULT_ITERATIONS, SQUARE);
    }

    public static MandelbrotSet ofCube() {
        return new MandelbrotSet(DEFAULT_ITERATIONS, CUBE);
    }

    public static MandelbrotSet ofPower(int pow) {
        if (!(1 < pow && pow < 22)) {
            throw new IllegalArgumentException("The mandelbrot function power needs to be between 2 and 9 inclusive");
        }
        MandelbrotFunction manFun = (cur, c) -> (cur.pow(pow)).add(c);

        return new MandelbrotSet(DEFAULT_ITERATIONS, manFun);
    }

    public static MandelbrotSet ofPoly(int... coefficients) {
        if (0 == coefficients.length || coefficients.length > 10) {
            throw new IllegalArgumentException("The mandelbrot parameters need to be more than one and up to 10 inclusive");
        }
        MandelbrotFunction manFun = (cur, c) -> {
            ComplexAny toRet = cur.multiply(0);
            int i = 0;
            while (i < coefficients.length) {
                toRet = toRet.add((cur.pow(i + 1)).multiply(coefficients[i]));
                i++;
            }
            return toRet.add(c);
        };

        return new MandelbrotSet(DEFAULT_ITERATIONS, manFun);
    }

}
