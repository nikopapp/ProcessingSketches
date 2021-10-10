package com.nikpappas.sketch.gol;

import com.nikpappas.processing.core.Trio;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ConwaysCube {
    private Map<Integer, Map<Integer, Map<Integer, Character>>> cube;
    private int extent = 0;
    private final int maxExtent;


    public ConwaysCube() {
        this(-1);
    }

    public ConwaysCube(int maxExtent) {
        this.maxExtent = maxExtent;
    }

    public void put(int x, int y, int z, char c) {
        calcExtent(x, y, z);
        if (cube == null) {
            cube = new ConcurrentHashMap<>();
        }
        cube.putIfAbsent(x, new ConcurrentHashMap<>());
        cube.get(x).putIfAbsent(y, new ConcurrentHashMap<>());
        cube.get(x).get(y).put(z, c);
    }

    public int getExtent() {
        return extent;
    }

    private void calcExtent(int x, int y, int z) {
        if (maxExtent == -1) {
            Stream.of(x, y, z).forEach(v -> {
                if (abs(v) > extent) extent = v;
            });
        } else {
            Stream.of(x, y, z).filter(a -> abs(a) < maxExtent).forEach(v -> {
                if (abs(v) > extent) extent = v;
            });
        }
    }

    public ConwaysCube clone() {
        ConwaysCube clone = new ConwaysCube();
        if (cube == null) {
            return null;
        }
        cube.forEach(((x, xvals) ->
                xvals.forEach((y, yvals) ->
                        yvals.forEach((z, c) -> clone.put(x, y, z, c)))));

        return clone;
    }

    public char get(int x, int y, int z) {
        if (cube.get(x) == null) {
            return '.';
        }
        if (cube.get(x).get(y) == null) {
            return '.';
        }
        if (cube.get(x).get(y).get(z) == null) {
            return '.';
        }
        return cube.get(x).get(y).get(z);

    }

    public void iterate() {
        ConwaysCube buffer = clone();
        int newExtent = getExtent() + 1;
        int newExtentExclusiveUpperLimit = newExtent + 1;
        IntStream.range(-newExtent, newExtentExclusiveUpperLimit).forEach(
                x -> IntStream.range(-newExtent, newExtentExclusiveUpperLimit).forEach(
                        y -> IntStream.range(-newExtent, newExtentExclusiveUpperLimit).forEach(
                                z -> {
                                    int alive = buffer.countAliveNeighbours(x, y, z);
                                    if (buffer.isAlive(x, y, z) && (alive != 2 && alive != 3)) {
                                        put(x, y, z, '.');
                                    } else if (!buffer.isAlive(x, y, z) && alive == 3) {
                                        put(x, y, z, '#');
                                    }
                                }
                        )
                )
        );
    }

    private int abs(int v) {
        if (v >= 0) {
            return v;
        } else {
            return -v;
        }
    }

    public int countAliveNeighbours(int x, int y, int z) {
        int alive = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if (i == 0 && j == 0 && k == 0) continue;
                    if (isAlive(x + i, y + j, z + k)) {
                        alive++;
                    }
                }
            }
        }
        return alive;
    }

    public boolean isAlive(int x, int y, int z) {
        return get(x, y, z) == '#';
    }


    public int countAlive() {
        return cube.values().stream()
                .mapToInt(x -> x.values().stream()
                        .mapToInt(y -> y.values().stream()
                                .mapToInt(z -> z.equals('#') ? 1 : 0).sum()).sum()).sum();

    }

    public Set<Trio<Integer>> getCoords() {
        Set<Trio<Integer>> trios = new HashSet<>();
        cube.forEach(((x, xvals) ->
                xvals.forEach((y, yvals) ->
                        yvals.forEach((z, c) -> trios.add(Trio.of(x, y, z))))));
        return trios;
    }

    public String toHashString() {
        StringBuilder res = new StringBuilder();
        cube.forEach(((x, xvals) ->
                xvals.forEach((y, yvals) ->
                        yvals.forEach((z, c) -> res.append(c)))));
        return res.toString();
    }

    public static ConwaysCube of(String[] init) {
        ConwaysCube cube = new ConwaysCube();
        for (int i = 0; i < init[0].length(); i++) {
            for (int y = 0; y < init.length; y++) {
                cube.put(i, y, 0, init[y].charAt(i));
            }
        }

        return cube;
    }

    public static ConwaysCube of(String[][] init) {
        ConwaysCube cube = new ConwaysCube();
        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[0].length; j++) {
                for (int k = 0; k < init[0][0].length(); k++) {
                    cube.put(i, j, k, init[i][j].charAt(k));
                }
            }
        }

        return cube;
    }
}
