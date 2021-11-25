package com.nikpappas.sketch.fractal;

import com.nikpappas.utils.collection.Pair;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;

public class Mandelbrot<coefficients> extends PApplet {
    private static final int NUM_OF_THREADS = getRuntime().availableProcessors() - 1;
    private static final int MAX_SIZE_OF_POINTS = 10000000;
    private List<ComplexAny> toDraw;
    private List<Thread> threads;
    private boolean nearMouse = false;
    private float mouseRange = 0.1f;
    float scale;
    //    private MandelbrotSet mandelbrot = MandelbrotSet.ofPower(20);

    int[] coefficients = new int[]{0, 1, 1, 1, 0, 1, -2,4};
    private boolean printPoly = true;
    private MandelbrotSet mandelbrot = MandelbrotSet.ofPoly(coefficients);


    public static void main(String[] args) {
        System.out.printf("Running with %d threads. (+1 main) %n", NUM_OF_THREADS);
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());

    }

    @Override
    public void settings() {
        size(900, 800);
        scale = (min(width, height) * (1f - 0.2f)) / 4f;
        toDraw = new ArrayList<>();
        Runnable findBrots = () -> {
            System.out.printf("Started broting %s%n", Thread.currentThread().getName());
            while (!Thread.currentThread().isInterrupted()) {

                ComplexAny c;
                if (nearMouse) {
                    c = Complex.of((mouseX - width / 2f) / scale + random(-mouseRange, mouseRange), (height / 2f - mouseY) / scale + random(-mouseRange, mouseRange));
                } else {
                    c = Complex.of(random(-2, 2), random(-2, 2));
                }
                if (mandelbrot.contains(c)) {
                    toDraw.add(c);
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
        if (printPoly) {
            printCoefficients();
        }
        pushMatrix();
        translate(width / 2, height / 2);

        // 20% margin
        noStroke();
        fill(55);
        // padding 10%
        circle(0, 0, scale * 4.5f);
        fill(255, 70);
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

    private void printCoefficients() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] == 0) {
                continue;
            }
            if (sb.length() > 0) {
                if (coefficients[i] > 0) {
                    sb.append(" + ");
                } else {
                    sb.append(" - ");
                }
            }
            if (coefficients[i] == 1) {
                sb.append(format("z^%d", i + 1));
            } else {
                if (i == 0) {
                    sb.append(format("%d*z", abs(coefficients[i])));
                } else {
                    sb.append(format("%d*z^%d", abs(coefficients[i]), i + 1));
                }
            }
        }
        text(sb.toString(), width / 2 - sb.length()*3, 20);
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
