package com.nikpappas.sketch;

import processing.core.PApplet;

public class XmasTree extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    private long timestamp = 0;

    @Override
    public void settings() {
        size(900, 600);
    }

    @Override
    public void draw() {
//        background(125);
        fill(100+random(100), 50, 60+random(20));

        translate(width / 2, height / 2);
        float w = t2w(timestamp);
        float amp = (timestamp%height * width * .5f * .001f);
        float extent = 30.0f*(timestamp%height)/200;
        circle(x(w, amp), y(timestamp), extent);
        circle(x(w+PI, amp), y(timestamp), extent);
        timestamp++;

    }

    private float t2w(long t) {
        return t / 60.0f;
    }

    private float x(float w, float amp) {
        return amp * cos(PI * w);
    }

    private float y(long t) {
        return t % height - height / 3;
    }

}
