package com.nikpappas.processing.patch;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

public class Clock implements Patch {
    private List<Patch> patches;

    public Clock(List<Patch> patches) {

        this.patches = patches;
    }

    @Override
    public void trigger() {
    }

    @Override
    public void iterate() {
        patches.stream().parallel()
                .flatMap(x -> x.getInputs().values().stream())
                .forEach(x -> {
//                    send(1);
                });

    }

    @Override
    public Map<Integer, Input> getInputs() {
        return emptyMap();
    }

    @Override
    public Map<Integer, Output> getOutputs() {
        return null;
    }


}
