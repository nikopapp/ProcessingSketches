package com.nikpappas.sketch;

import com.nikpappas.processing.patch.Clock;
import com.nikpappas.processing.patch.Patch;
import com.nikpappas.utils.collection.Couple;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.util.Collections.emptyList;

public class Patcher<prevBeat> extends PApplet {

    Map<Couple<Integer>, Patch> patches = new HashMap<>();
    long prevBeat = currentTimeMillis();

    int bpm = 120;
    int delayInMs = 1000 * 60 / 120;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
        super.settings();
        size(800, 600);
    }

    @Override
    public void setup() {
        super.setup();
    }

    @Override
    public void draw() {

        noStroke();
        rect(0, 0, width, 30);
        fill(30, 30, 30);
        rect(10, 10, 20, 20);
        super.draw();
        long now = currentTimeMillis();
        long diff = delayInMs - (now - prevBeat);
        delay((int) diff);
        long prevBeat = now;
    }

    @Override
    public void mouseClicked() {
        if (mouseX > 10 && mouseX < 20 && mouseY > 10 && mouseY < 20) {
            addPatch();
        }
        super.mouseClicked();
    }

    public void addPatch() {
        Patch movingPatch = new Clock(emptyList());

    }

}
