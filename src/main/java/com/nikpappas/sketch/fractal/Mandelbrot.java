package com.nikpappas.sketch.fractal;

import processing.core.PApplet;

public class Mandelbrot extends PApplet {
    public static final int MAX_ITER = 80;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(900,800);
    }

    @Override
    public void setup() {
        background(33);
    }

    @Override
    public void draw() {
//        for a in range(-10, 10, 5):
//        for b in range(-10, 10, 5):
//        c = complex(a / 10, b / 10)
//        print(c, mandelbrot(c))


    }

    private int mandelbrot(int c){
        int z=0;
        int n=0;
        while (abs(z) <= 2 && n < MAX_ITER){
            z = z*z+c;
            n++;
        }
        return n;
    }



}
