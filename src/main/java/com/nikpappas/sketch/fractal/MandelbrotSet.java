package com.nikpappas.sketch.fractal;

public class MandelbrotSet {
    public static boolean contains(ComplexAny c) {
        return isMandelbrot(c);
    }

    private static boolean isMandelbrot(ComplexAny c) {
        double limit = 2;
        int maxIterations = 2000000;
        ComplexAny cur = c;
        for (int i = 0; i < maxIterations; i++) {
//            System.out.println(i);
            cur = (cur.square()).add(c);
//            System.out.println(cur);
            if (cur.abs() > (limit)) {
                return false;
            }
        }
        return true;
    }

}
