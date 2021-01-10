package com.nikpappas.sketch.gol;

import processing.core.PApplet;

import static com.nikpappas.processing.benchmark.Timing.timed;

public class GameOfLife3D extends PApplet {

    private final ConwaysCube cube = new ConwaysCube(12);

    float rotation = 0;
    int firsttimestamp = millis();
    float timestamp = 0f;
    int renderTime = 0;
    boolean pause = true;
    float scaleC = 20;
    float colourScale = 20;
    private static final int BACKGROUND =200;


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
//        fullScreen(P3D);
        fullScreen(P3D, 1);
//        size(900, 800, P3D);
        char[][] init = new char[][]{
                {'.', '#', '.'},
                {'.', '#', '.'},
                {'.', '#', '.'},
        };

        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[i].length; j++) {
                cube.put(i - init.length / 2, j - init[i].length / 2, 0, init[i][j]);
            }
        }

    }

    @Override
    public void setup() {
        background(BACKGROUND);
    }

    @Override
    public void draw() {
        background(BACKGROUND);
        pushMatrix();
        translate(width / 2, height / 2, 0);

        int extent = cube.getExtent();
        float orbitRadius = mouseX / 2 + 30 * extent;
        float ypos = (mouseY - (height / 2)) / 3 * extent;
        float xpos = cos(radians(rotation)) * orbitRadius;
        float zpos = sin(radians(rotation)) * orbitRadius;

        camera(xpos, ypos, zpos, 0, 0, 0, 0, -1, 0);

        timed(this::drawCubes, "draw");
        popMatrix();
        int curIteration = millis() - firsttimestamp;
        if (curIteration - timestamp > 1000 && !pause) {
            timestamp = curIteration;
            timed(cube::iterate, "Iterate");
        }
        int delayTime = 30 - (curIteration - renderTime);
        renderTime = curIteration;
        if (delayTime > 0) {
            delay(delayTime);
        } else {
            delay(6);
        }
        rotation += 0.2;
    }


    private void drawCubes() {
        cube.getCoords().forEach(t -> {
            if (cube.isAlive(t._1, t._2, t._3)) {
                pushMatrix();
                translate(t._1 * scaleC, t._2 * scaleC, t._3 * scaleC);
                stroke(abs(t._1) * 4*colourScale/5, abs(t._2) * 4*colourScale/5, abs(t._3) * 4*colourScale/5);
                fill(abs(t._1) * colourScale, abs(t._2) * colourScale, abs(t._3) * colourScale);
                box(10);
                popMatrix();
            } else {
//                box(2);
            }
        });

    }

    @Override
    public void keyPressed() {
        if (key == ' ') { // On/off of pause
            pause = !pause;
        }
    }
}
