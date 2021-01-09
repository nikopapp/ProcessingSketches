package com.nikpappas.sketch.shapes3d;

import processing.core.PApplet;

public class Sphere extends PApplet {

    private static final int BACKGROUND = 33;
    private static final float RADIUS = 100;
    private static final float[] SIGS = new float[]{1,-1};


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(1100, 900, P3D);
    }

    @Override
    public void setup() {
        background(BACKGROUND);
//        noFill();
        noStroke();

    }

    @Override
    public void draw() {

        pushMatrix();
        translate(width *.5f , height *.5f);
        float x = random(-RADIUS, RADIUS);
        float y = randSig()*random(sqrt(  sq(RADIUS)-sq(x)));
        float z = sqrt(  sq(RADIUS)-sq(x)-sq(y));
        fill(150+z/5);
        System.out.println(x+","+y);

        circle(x, y, 10);
        popMatrix();
    }

    private float randSig(){
        return SIGS[round(random(1))];
    }


}
