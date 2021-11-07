package com.nikpappas.sketch.shapes3d;

import com.nikpappas.utils.collection.Trio;
import processing.core.PApplet;

import java.util.LinkedList;
import java.util.List;

public class SpiralSlow extends PApplet {

    private static final int BACKGROUND = 2;

    public static final int PARTICLES = 500;

    List<Particle> particles;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
//        size(1100, 900, P3D);
        fullScreen(P3D);

    }

    @Override
    public void setup() {
        background(BACKGROUND, BACKGROUND + 15, BACKGROUND + 10);
//        noStroke();
        particles = new LinkedList<>();

        Thread thread = new Thread(new ParticleAdder());
        thread.start();

        frameRate(30);

    }


    @Override
    public void draw() {
        background(BACKGROUND, BACKGROUND + 15, BACKGROUND + 10);
        stroke(244, 0.1f);

        pushMatrix();
        translate(width * .5f, height * .5f);

        for (int i = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            if(particle.lifespan>particle.time){
                Trio<Float> c = particle.tick();
                point(c._1, c._2, c._3);
                circle(c._1, c._2, 4);
            }
        }

        popMatrix();
    }

    private class Particle {
        private final int lifespan;
        private int rspeed;
        private int time;

        Particle(int lifespan, int rspeed) {
            this.lifespan = lifespan;
            this.rspeed = rspeed;
        }

        Trio<Float> tick() {
//            Trio<Float> next = Trio.of(600*sin(time), .1f * time, .01f * time);
            Trio<Float> next = Trio.of(rspeed * sin(time / 600f + 1), .1f * time, .01f * time);
            time++;
            return mapToCart(next);

        }

        Trio<Float> mapToCart(Trio<Float> polar) {
            float x = (polar._1 * cos(polar._2) * sin(polar._3));
            float y = (polar._1 * sin(polar._2) * sin(polar._3));
            float z = (polar._1 * cos(polar._3));
            return Trio.of(x, y, z);
        }
    }

    private class ParticleAdder implements Runnable {
        @Override
        public void run() {
            while (true) {
                particles.add(new Particle((int) random(500, 1000), (int) random(300, 900)));
                try {
                    Thread.sleep((int) random(250, 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (particles.size() > PARTICLES) {
                    return;
                }
            }
        }
    }
}

