package com.nikpappas.sketch.fractal;

import com.nikpappas.utils.collection.Pair;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mandelbrot extends PApplet {
    private static final int NUM_OF_THREADS = 10;
    private static final int MAX_SIZE_OF_POINTS = 10000000;
    private List<ComplexAny> toDraw;
    private List<Thread> threads;
    private boolean nearMouse = true;
    private float mouseRange = 0.1f;
    float scale;


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(900, 800);
        scale = (min(width, height) * (1f - 0.2f)) / 4f;
        toDraw = new ArrayList<>();
        Runnable findBrots = () -> {
            System.out.println("StartedBroting");
            while (!Thread.currentThread().isInterrupted()) {

                ComplexAny c;
                if (nearMouse) {
                    c = Complex.of((mouseX-width/2f) / scale + random(-mouseRange, mouseRange), (height/2f -mouseY) / scale + random(-mouseRange, mouseRange));
                } else {
                    c = Complex.of(random(-2, 2), random(-2, 2));
                }
                if (MandelbrotSet.contains(c)) {
                    toDraw.add(c);
//                System.out.println(toDraw.size() + " - " + c + ": Is mandel");
                }
            }
        };

        threads = IntStream.range(0, NUM_OF_THREADS)
                .mapToObj(x -> Pair.of(x, findBrots))
                .map(x -> new Thread(x._2, "T" + x._1))
                .collect(Collectors.toList());
        threads.forEach(Thread::start);
    }

    @Override
    public void setup() {
        frameRate(60);
        background(55);
    }

    @Override
    public void draw() {
        background(130);
        int toDrawSize = toDraw.size();
        text(toDrawSize, 10, 10);
        pushMatrix();
        translate(width / 2, height / 2);

        // 20% margin
        noStroke();
        fill(55);
        // padding 10%
        circle(0, 0, scale * 4.5f);
        fill(255, 60);
        for (int i = 0; i < toDrawSize; i++) {
            ComplexAny x = toDraw.get(i);
            float coordX = (float) (scale * x.real());
            float coordY = (float) -(scale * x.imaginary());
            circle(coordX, coordY, 1);
//            text(i+1, coordX - 5, coordY - 5);
        }
        if (toDrawSize > MAX_SIZE_OF_POINTS) {
            interrupt();
        }
        popMatrix();
    }


    void interrupt() {
        threads.stream()
                .filter(Thread::isAlive)
                .forEach(x -> {
                    x.interrupt();
                    System.out.println("interrrupting " + x.getName());
                });
    }
}
