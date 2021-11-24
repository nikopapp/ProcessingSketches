package com.nikpappas.sketch.fractal;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Mandelbrot extends PApplet {
    private static final int NUM_OF_THREADS = 2;
    private List<ComplexAny> toDraw;

    Runnable findBrots = () -> {
        System.out.println("StartedBroting");
        while (true) {
            ComplexAny c = ComplexBD.of(random(-2, 2), random(-2, 2));
            if (MandelbrotSet.contains(c)) {
                toDraw.add(c);
                System.out.println(toDraw.size() + " - " + c + ": Is mandel");
            }
        }
    };

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(900, 800);
        toDraw = new ArrayList<>();
        IntStream.range(0, NUM_OF_THREADS)
                .mapToObj(x -> findBrots)
                .map(Thread::new)
                .forEach(Thread::start);
    }

    @Override
    public void setup() {
        background(55);
    }

    @Override
    public void draw() {
        background(55);
        pushMatrix();
        translate(width / 2, height / 2);


        float scale = 140f;
        stroke(144);
        noFill();
        circle(0, 0, scale * 4);
        noStroke();
        fill(255);
        int i = 1;
        for (ComplexAny x : toDraw) {
            float coordX = (float) (scale * x.real());
            float coordY = (float) -(scale * x.imaginary());
            circle(coordX, coordY, 5);
            text(i++, coordX - 5, coordY - 5);
        }

        delay(100);


        popMatrix();
    }


}
