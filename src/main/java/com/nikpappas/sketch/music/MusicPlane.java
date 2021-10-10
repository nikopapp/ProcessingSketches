package com.nikpappas.sketch.music;

import processing.core.PApplet;
import processing.sound.FFT;
import processing.sound.SoundFile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MusicPlane extends PApplet {

    private int timestamp;
    private Deque<List<Float>> buffer;
    private FFT fft;
    private static final int BANDS = 128;
    private static final int PLANES = 64;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {

        smooth(10);
        timestamp = 0;
        buffer = new ArrayDeque<>();
        size(900, 600, P3D);


        // Load and play a soundfile and loop it.
//        SoundFile sample = new SoundFile(this, "/Users/nikos/Music/iTunes/iTunes Media/Music/Unknown Artist/Unknown Album/sagapw.wav");
         SoundFile sample = new SoundFile(this, "/Users/nikos/Music/SonicPi/MelodyMoody.wav");
        sample.loop();

        // Create the FFT analyzer and connect the playing soundfile to it.
        fft = new FFT(this, BANDS);
        fft.input(sample);
    }

    @Override
    public void draw() {
        // Set background color, noStroke and fill color
        background(17, 15, 16);
        noFill();

        recalculate();
        drawPlanes();
        timestamp++;
        delay(20);

    }

    private void drawPlanes() {
        pushMatrix();
        translate(width, height);
        rotateX(-PI / 2);
        rotateZ(PI / 2);
        rotateY(-PI / 9);
        // ejes
//        line(0, 0, 0, 1000, 0, 0);
//        line(0, 0, 0, 0, 1000, 0);

        drawLines();
//        drawPoints();
        popMatrix();
    }

    private void recalculate() {
        if (timestamp % 10 != 0) return;

        // Perform the analysis
        fft.analyze();
        List<Float> plane = new ArrayList<>();
        // closig the line
        plane.add(0f);
        for (float val : fft.spectrum) {
            plane.add(val);
        }
        buffer.add(plane);
        if (buffer.size() > PLANES) {
            buffer.pop();
        }
    }

    private void drawPoints() {
        int i = 0;
        for (List<Float> plane : buffer) {
            for (int j = 0; j < plane.size(); j++) {
                stroke(300 * plane.get(j) + 100);
                fill(200);
                int x = i * 30 + 10;
                int y = j * 30;
                float z = -3000 * log(1 + plane.get(j));

                point(x, y, z);
            }
            i++;
        }
    }

    private void drawLines() {
        int xScale = 30;
        int yScale = 20;
        int zScale = 700;
        int i = 0;
        for (List<Float> plane : buffer) {
            int x = i * xScale;
            stroke(100+x - random(50), random(70), 100);
            for (int j = 1; j < plane.size() - 60; j++) {
                int y1 = (j - 1) * yScale;
                float z1 = -zScale * plane.get(j - 1);
                int y2 = j * yScale;
                float z2 = -zScale * plane.get(j);
                stroke(100+x - random(50), -(z2+z1), 100);

//                stroke(random(255), 70, z2+100);
//                stroke(random(190), y2-70, z2+100);
//                stroke(x, y2-70, z2+100);
//                stroke(x -random(50), y2-random(70), z2);
//                stroke(10*x/xScale , 10*y1/yScale, -z1*.5f);
//                stroke(random(255), random(y2-plane.size()), z2+100);
                line(x, y1, z1, x, y2, z2);
            }
            i++;
        }


    }
}
