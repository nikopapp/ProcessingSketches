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
    public void setup() {
        background(40, 60,50);
    }

    @Override
    public void draw() {
//        colorMode(RGB, 255.0f , 255.0f, 255.0f, 1);
//        background(90);
        translate(width / 2, height / 2);
        float w = t2w(timestamp);
        float amp = (timestamp%height * width * .5f * .001f);
        float extent = 23.0f*(timestamp%height)/200;
        drawColouredCircle(x(w, amp), y(timestamp), extent);
        drawColouredCircle(x(w+PI*.5f, amp), y(timestamp), extent);
        drawColouredCircle(x(w+PI*1.5f, amp), y(timestamp), extent);
        drawColouredCircle(x(w+PI, amp), y(timestamp), extent);
        drawPoints(50);
        timestamp++;

    }
    private void drawPoints(int limit){
        stroke(255,70);
        for(int i=0;i<limit;i++){
            point(random(width)-width/2, random(height)-height/2);
        }

    }
    private void drawColouredCircle(float x,float y,float extent){
        stroke(30,30,30,30);

        fill(100+random(100), 50, 60+random(30), 30);
        circle(x,y,extent);

    }

    private float t2w(long t) {
        return 2*PI *t / 100.0f;
    }

    private float x(float w, float amp) {
        return amp * cos( w);
    }

    private float y(long t) {
        return t % height - height / 3;
    }

}
