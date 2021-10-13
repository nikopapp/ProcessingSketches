package com.nikpappas.sketch.gol;

import com.nikpappas.processing.core.Trio;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.util.Collections.emptySet;
import static java.util.stream.IntStream.range;

public class ConwaysCube {
    private Map<Integer, Map<Integer, Map<Integer, Character>>> cube;
    private final int maxExtent;
    private int posExtent = 0;
    private int minExtent;


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

    public int getPosExtent() {
        return posExtent;
    }

    public int getMinExtent() {
        return minExtent;
    }

    private void calcExtent(int x, int y, int z) {
        if (maxExtent == -1) {
            Stream.of(x, y, z).forEach(v -> {
                if (v > posExtent) posExtent = v;
                if (v < minExtent) minExtent = v;
            });
        } else {
            Stream.of(x, y, z).filter(a -> abs(a) <= maxExtent).forEach(v -> {
                if (v > posExtent) posExtent = v;
                if (v < minExtent) minExtent = v;
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
        int newMinExtent = getMinExtent() - 2;
        int newPosExtent = getPosExtent() + 2;
        range(newMinExtent, newPosExtent).forEach(
                x -> range(newMinExtent, newPosExtent).forEach(
                        y -> range(newMinExtent, newPosExtent).forEach(
                                z -> {
                                    int alive = buffer.countAliveNeighbours(x, y, z);
                                    if (buffer.isAlive(x, y, z) && (5 >= alive || alive >= 13)) {
                                        put(x, y, z, '.');
                                    } else if (!buffer.isAlive(x, y, z) && 11 <= alive && alive <= 15) {
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
        if (cube == null) {
            return emptySet();
        }
        Set<Trio<Integer>> trios = new HashSet<>();
        cube.forEach(((x, xvals) ->
                xvals.forEach((y, yvals) ->
                        yvals.forEach((z, c) -> trios.add(Trio.of(x, y, z))))));
        return trios;
    }


    public String toHashString() {
        StringBuilder res = new StringBuilder();
        range(minExtent, posExtent + 1).forEach(x ->
                range(minExtent, posExtent + 1).forEach(y ->
                        range(minExtent, posExtent + 1).forEach(z -> {
                            res.append(get(x, y, z));
                        })));

        return res.toString();
    }

    public int getAbsExtent() {
        return max(abs(minExtent), posExtent);
    }

    public String toHashStringBounding() {
        StringBuilder res = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        int extentCur = getAbsExtent();
        range(-extentCur, extentCur + 1).forEach(x ->
                range(-extentCur, extentCur + 1).forEach(y ->
                        range(-extentCur, extentCur + 1).forEach(z -> {
                            char c = get(x, y, z);
                            if (res.length() == 0 && c == '#') {
                                res.append(c);
                            } else if (res.length() > 0) {
                                buffer.append(c);
                                if (c == '#') {
                                    res.append(buffer);
                                    buffer.setLength(0);
                                }
                            }
                        })));
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

    private static ConwaysCube of(List<List<String>> init) {
        ConwaysCube cons = new ConwaysCube(-1);
        for (int i = 0; i < init.size(); i++) {
            for (int j = 0; j < init.get(i).size(); j++) {
                for (int z = 0; z < init.get(i).get(j).length(); z++) {
                    cons.put(i, j, z, init.get(i).get(j).charAt(z));
                }

            }
        }
        return cons;
    }

    public String[][] toStringArray() {
        if (getCoords().isEmpty()) {
            return new String[][]{{}};
        }
        int maxXCoord = getCoords().stream().mapToInt(x -> x._1).max().orElse(maxExtent);
        int minXCoord = getCoords().stream().mapToInt(x -> x._1).min().orElse(minExtent);
        int xLimit = maxXCoord - minXCoord + 1;
        int maxYCoord = getCoords().stream().mapToInt(x -> x._2).max().orElse(maxExtent);
        int minYCoord = getCoords().stream().mapToInt(x -> x._2).min().orElse(minExtent);
        int yLimit = maxYCoord - minYCoord + 1;
        int maxZCoord = getCoords().stream().mapToInt(x -> x._3).max().orElse(maxExtent);
        int minZCoord = getCoords().stream().mapToInt(x -> x._3).min().orElse(minExtent);
        int zLimit = maxZCoord - minZCoord + 1;
        String[][] res = new String[xLimit][yLimit];
        range(0, xLimit).forEach(x -> {
            range(0, yLimit).forEach(y -> {
                StringBuilder sb = new StringBuilder();
                range(0, zLimit).forEach(z -> {
                    sb.append(get(x + minXCoord, y + minYCoord, z + minZCoord));
                });
                res[x][y] = sb.toString();
            });
        });

        return res;
    }

    public static ConwaysCube parse(String input) {
        List<List<String>> init = Arrays.stream(input.split("--"))
                .map(String::trim).map(x -> Arrays.stream(x.split(","))
                        .map(s -> s.replace("[", "").replace("]", "").trim())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return of(init);
    }
}
