package com.nikpappas.sketch.robotics;

import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class LegAnimation extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }


    private Map<Integer, Node> nodes = new HashMap<>();

    {
        nodes.put(1, Node.of(30, 30));
        nodes.put(2, Node.of(60, 60));
        nodes.put(3, Node.of(40, 90));
    }

    private Map<Integer, Element> elements = new HashMap<>();

    {
        elements.put(1, Element.of(nodes.get(1), nodes.get(2)));
        elements.put(2, Element.of(nodes.get(2), nodes.get(3)));
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void draw() {
        elements.values().forEach(e -> {
            line(e.nodes._1.x, e.nodes._1.y, e.nodes._2.x, e.nodes._2.y);
        });
        delay(100);
        mouseDragged();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        System.out.println(event);

    }

}
