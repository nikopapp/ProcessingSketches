package com.nikpappas.sketch.gol;

import processing.core.PApplet;

public class GameOfLife3D extends PApplet {

    private final ConwaysCube cube = new ConwaysCube();

    float rotation = 0;
    int firsttimestamp = millis();
    float timestamp = 0f;


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        size(900, 800, P3D);
        char[][] init = new char[][]{
                {'.', '#', ','},
                {'.', '#', ','},
                {'#', '#', '#'},
        };

        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[i].length; j++) {
                cube.put(i - init.length / 2, j - init[i].length / 2, 0, init[i][j]);
            }
        }

    }

    @Override
    public void setup() {
        background(33);
    }

    @Override
    public void draw() {
        stroke(130);
        fill(200);
        background(33);
        pushMatrix();
        translate(width / 2, height / 2, 0);
        float orbitRadius = mouseX / 2 + 50;
        float ypos = mouseY / 3;
        float xpos = cos(radians(rotation)) * orbitRadius;
        float zpos = sin(radians(rotation)) * orbitRadius;

        camera(xpos, ypos, zpos, 0, 0, 0, 0, -1, 0);
        float scaleC = 20;

        cube.getCoords().forEach(t -> {
            pushMatrix();
            translate(t._1 * scaleC, t._2 * scaleC, t._3 * scaleC);
            if (cube.isAlive(t._1, t._2, t._3)) {
                box(2);
            } else {
                box(10);
            }
            popMatrix();
        });
        popMatrix();
        int curIteration = millis()-firsttimestamp;
        if (curIteration-timestamp>1000){
            timestamp = curIteration;
            cube.iterate();
        }
        delay(50);
    }

}
