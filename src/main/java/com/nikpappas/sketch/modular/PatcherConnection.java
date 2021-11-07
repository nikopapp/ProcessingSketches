package com.nikpappas.sketch.modular;

import com.nikpappas.processing.core.Couple;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;

public class PatcherConnections {

    int id1;
    int id2;
    List<Couple<Float>> pins;

    private PatcherConnections(int id1, int id2, List<Couple<Float>> pins) {
        this.id1 = id1;
        this.id2 = id2;
        this.pins = pins;
    }


    public static PatcherConnections of(int id1, int id2) {
        return new PatcherConnections(id1, id2, emptyList());
    }
}
