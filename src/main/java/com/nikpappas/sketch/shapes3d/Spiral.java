package com.nikpappas.sketch.shapes3d;

import com.nikpappas.processing.core.Trio;
import processing.core.PApplet;

import java.util.ArrayList;

public class Spiral extends PApplet {

    private static final int BACKGROUND = 2;

    ArrayList<Particle> particles;

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
        particles = new ArrayList<>();
        particles.add(new Particle(1000));

    }


    @Override
    public void draw() {
//        background();
        stroke(244);

        pushMatrix();
        translate(width * .5f, height * .5f);

        particles.forEach(x -> {
            Trio<Float> c = x.tick();
            point(c._1, c._2, c._3);
            circle(c._1, c._2, 10);


        });

        popMatrix();
    }

    private class Particle {
        private final int lifespan;
        private int time;

        Particle(int lifespan) {
            this.lifespan = lifespan;
        }

        Trio<Float> tick() {
            Trio<Float> next = Trio.of(1f * time, .1f * time, 10f * time);
            time++;
            return mapToCart(next);

        }

        Trio<Float> mapToCart(Trio<Float> polar) {
            float x = (float) (polar._1 * cos(polar._2) * cos(polar._3));
            float y = (float) (polar._1 * sin(polar._2) * sin(polar._3));
            float z = (float) (polar._1 * sin(polar._3));
            return Trio.of(x, y, z);
        }
    }
}

