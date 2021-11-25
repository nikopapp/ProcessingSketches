package com.nikpappas.sketch.robotics;


import com.nikpappas.utils.collection.Couple;

public class Element {
    Couple<Node> nodes;

    private Element(Node _1, Node _2) {
        this.nodes = Couple.of(_1, _2);
    }

    public static Element of(Node _1, Node _2) {
        return new Element(_1, _2);
    }
}
