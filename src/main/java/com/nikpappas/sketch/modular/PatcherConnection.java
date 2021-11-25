package com.nikpappas.sketch.modular;


import com.nikpappas.utils.collection.Couple;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class PatcherConnection {

    int id1;
    int id2;
    List<Couple<Float>> pins;

    private PatcherConnection(int id1, int id2, List<Couple<Float>> pins) {
        this.id1 = id1;
        this.id2 = id2;
        this.pins = pins;
    }

    public static PatcherConnection of(int id1, int id2) {
        return new PatcherConnection(id1, id2, new ArrayList<>());
    }
}
