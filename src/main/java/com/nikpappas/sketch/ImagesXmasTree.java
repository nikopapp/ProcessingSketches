package com.nikpappas.sketch;

import processing.core.PApplet;

public class ImagesXmasTree extends PApplet {


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    private double timestamp = 0;

    @Override
    public void settings() {
        size(900, 600);
    }

    @Override
    public void setup() {
        background(40, 60, 50);
    }

    @Override
    public void draw() {
//        colorMode(RGB, 255.0f , 255.0f, 255.0f, 1);
        transparentBackground(30, 60, 40, 1.8f);
        translate(width / 2, height / 2);
        double treeHeight = timestamp % (height*.66);
        double w = t2w(timestamp);
        double amp = (treeHeight * width * .5f * .001f);
        double extent = 30.0f * (treeHeight) / 200;
        drawColouredCircle(x(w, amp), y(treeHeight), extent);
        drawColouredCircle(x(w + PI * .5f, amp), y(treeHeight), extent);
        drawColouredCircle(x(w + PI * 1.5f, amp), y(treeHeight), extent);
        drawColouredCircle(x(w + PI, amp), y(treeHeight), extent);
        drawPoints(30);
        timestamp += .35;
        saveFrame("ImagesXmasTree-####.jpeg");

    }

    private void transparentBackground(int i, int i1, int i2, float v) {
        fill(i, i1, i2, v);
        rect(0, 0, width, height);
    }

    private void drawPoints(int limit) {
        stroke(255, 70);
        for (int i = 0; i < limit; i++) {
            point(random(width) - width / 2, random(height) - height / 2);
        }

    }

    private void drawColouredCircle(double x, double y, double extent) {
        stroke(30, 30, 30, 30);

        fill(140 + random(100), 50, 50 + random(30), 30);
        circle((float) x, (float) y, (float) extent);

    }

    private double t2w(double t) {
        return 2 * PI * t / 10.0;
    }

    private double x(double w, double amp) {
        return amp * Math.cos(w);
    }

    private double y(double t) {
        return 2.1 * (t % height * .66) - height * .33;
    }

}

