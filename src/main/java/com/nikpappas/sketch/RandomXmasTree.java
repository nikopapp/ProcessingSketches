package com.nikpappas.sketch;

import com.nikpappas.processing.random.RandomPicker;
import processing.core.PApplet;

import java.awt.*;
import java.util.List;

import static java.util.Arrays.asList;

public class RandomXmasTree extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    private final static List<Color> colours = asList(
            new Color(140, 60, 70, 50),
            new Color(130, 60, 70, 50),
            new Color(20, 160, 60, 40),
            new Color(220, 200, 120, 20)
    );


    private final static RandomPicker<Color> colourPicker = new RandomPicker<>(colours);
    private long timestamp = 0;


    @Override
    public void settings() {
        size(900, 600);
    }

    @Override
    public void setup() {
        background(30, 34, 29);
    }

    @Override
    public void draw() {
//        colorMode(RGB, 255.0f , 255.0f, 255.0f, 1);
//        background(90);

        transparentBackground(1, 2, 1, 1.3f);
        translate(width / 2, height / 2);

        double treeHeight = timestamp % (height * .66);
        float w = t2w(timestamp);
        double amp = (treeHeight * width * .5f * .001f);
        float random2 = (int) random(10000);
        double extent = 30.0f * (treeHeight) / 200;
        drawColouredCircle(x(w, amp), y(timestamp), extent);
        extent = 30.0f * (treeHeight) / 200;
        drawColouredCircle(x(w + PI * .5f, amp), y((int) treeHeight), extent);
        if((int)timestamp %2==0){
            fill(210, 200, 60, 20);
            circle(15 * sin(w) + 15 * sin(w / 2), -height / 3 + 15 * cos(w) + 15 * (cos(w / 2)), max((float) extent - 20, 0));
        }

        timestamp = (int) random(10000);
        saveFrame("XmasTree-#####.jpeg");
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    private void drawColouredCircle(double x, double y, double extent) {
        stroke(200, 200, 200, 10);

        Color colour = colourPicker.random();

        fill(colour.getRed() + random(80), colour.getGreen(), colour.getBlue() + random(30), 30);
        circle((float) x+ random((float)x/80), (float) y+random((float)x/100), (float) extent);

    }
    private void transparentBackground(int i, int i1, int i2, float v) {
        fill(i, i1, i2, v);
        rect(0, 0, width, height);
    }


    private float t2w(long t) {
        return 2 * PI * t / 60.0f;
    }

    private double x(float w, double amp) {
        return amp * cos(w);
    }

    private double y(long t) {
        return t % (height * .66) - height / 3;
    }

}
