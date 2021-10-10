package com.nikpappas.sketch.fractal;

import com.nikpappas.processing.core.Couple;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class KochSnowflake extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    public static final int SCALE = 100;
    List<Couple<Float>> points = new ArrayList<>();

    {
        points.add(Couple.of(0f, -sqrt(1f * SCALE)));
        points.add(Couple.of(0.5f * SCALE, 0f));
        points.add(Couple.of(-0.5f * SCALE, 0f));
    }

    int iterations = 0;

    @Override
    public void settings() {
        size(900, 800);
    }

    @Override
    public void setup() {
        background(33);
        stroke(200);
    }

    @Override
    public void draw() {
        pushMatrix();
        background(33);
        translate(width / 2, height / 2);
        for (int i = 0; i <= points.size(); i++) {
            Couple<Float> first = points.get(i % points.size());
            Couple<Float> last = points.get((i + 1) % (points.size()));
            line(first._1, first._2, last._1, last._2);
            System.out.println(i + " Drwwa line " + first + " " + last + " " + dist(first._1, first._2, last._1, last._2));
        }
        popMatrix();
    }

    private void calc() {

    }
}
