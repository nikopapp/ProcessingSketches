package com.nikpappas.processing.math;


import static com.nikpappas.processing.math.Util.sq;

public class Distances {
    public static double dist(float x1, float y1, float x2, float y2) {
        return Math.sqrt(sq(x2 - x1) + sq(y2 - y1));
    }
}
