package com.nikpappas.sketch;

import processing.core.PApplet;

import static java.util.stream.IntStream.range;

public class RoomNightBlur extends PApplet {

    private static final int BACKGROUND = 2;


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(1100, 900, P3D);
//        fullScreen(P3D);
    }

    @Override
    public void setup() {
        background(BACKGROUND, BACKGROUND + 15, BACKGROUND + 10);
//        frameRate(30);

    }


    @Override
    public void draw() {
        range(0, 10).forEach((i) ->{
//            rwidth = 10;
//            ofs
            rect(0,i, 0,0);
        });
    }


}
