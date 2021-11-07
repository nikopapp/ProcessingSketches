package com.nikpappas.sketch.modular;

public class PatcherModule {
    String name;
    float x;
    float y;
    float size;


    private PatcherModule(String name, float x, float y, float size) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.size = size;
    }


    public static PatcherModule of(String name, float x, float y) {
        return new PatcherModule(name, x, y, 20);
    }

}
