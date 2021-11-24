package com.nikpappas.processing.patch;

import java.util.Map;

public interface Patch {
    void trigger();

    void iterate();


    Map<Integer, Input> getInputs();

    Map<Integer, Output> getOutputs();

}
