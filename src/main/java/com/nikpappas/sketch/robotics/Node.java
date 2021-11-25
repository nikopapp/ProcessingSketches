package com.nikpappas.sketch.robotics;

public class Node {
    float x;
    float y;

    private Node(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Node of(float x, float y) {
        return new Node(x, y);
    }
}

