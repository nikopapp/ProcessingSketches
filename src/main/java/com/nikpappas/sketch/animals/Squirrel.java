package com.nikpappas.sketch.animals;

import com.nikpappas.utils.collection.Pair;
import processing.core.PApplet;

public class Squirrel extends PApplet {
    private long initialTimestamp;
    private long timestamp;
    private static final float scale = 6;
    private static final int BACKGROUND = 33;
//    private static final int  = 33;


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(1100, 900);
    }

    @Override
    public void setup() {
        initialTimestamp = System.currentTimeMillis();
        timestamp = initialTimestamp;
        background(BACKGROUND);
        stroke(200);
//        noFill();

    }

    @Override
    public void draw() {
        background(BACKGROUND);
        pushMatrix();
        translate(0, height / 2f);
        stroke(200);
        strokeWeight(3);
        noFill();

        long currentTimeStamp = System.currentTimeMillis();
        float t = (currentTimeStamp - initialTimestamp) * 0.006f;

        Pair<Float, Float> f = Pair.of((scale * 10 * t) % width, -6 * scale * f(t));
        float rDelay =  PI / 4 * sin( 2*t);
        Pair<Float, Float> r = Pair.of(((scale * 10 * (t - rDelay)) -10)% width, -6 * scale * f(t - rDelay));
        if (f._1 > r._1) {
            line(r._1, r._2, f._1, f._2);
        } else {
            point(f._1, f._2);
        }

        arc(r._1, r._2 - 30, 60, 60, HALF_PI, PI);
        arc(r._1 - 35, r._2 - 30, 10, 10, PI, 2 * PI);

        smartDelay(currentTimeStamp);
        popMatrix();
        timestamp = currentTimeStamp;
    }


    public float f(float x) {
        return Math.abs(sin(x));
    }

    private void smartDelay(long currentTimeStamp) {
        long del = 25 - (currentTimeStamp - timestamp);

        if (del > 0) {
            delay((int) del);
        }

    }
}
