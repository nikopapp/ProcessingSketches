package com.nikpappas.sketch.gol;

import com.nikpappas.processing.core.Couple;
import com.nikpappas.processing.core.Triplet;

import java.util.*;

import static com.nikpappas.sketch.gol.State.*;
import static java.util.stream.Collectors.toList;


public class CombiAnaliser {
    public static final int ITERATIONS = 10;

    public static void main(String[] args) {
        GolInitialPositionCalc combiCalc = new GolInitialPositionCalc();

        List<String[]> combinations = combiCalc.generateCombinations(4, 4);
        Map<Integer, String[]> withIndex = new TreeMap<>();
        for (int i = 0; i < combinations.size(); i++) {
            withIndex.put(i, combinations.get(i));
        }

        System.out.println("# combi:" + combinations.size());
        List<Triplet<String[], Couple<Integer>, State>> results = withIndex.entrySet().stream()
                .parallel()
                .map(x -> {
                    String[] v = x.getValue();
                    if (x.getKey() % 10 == 0) {
                        System.out.println(x.getKey());
                    }
                    ConwaysCube cube = ConwaysCube.of(v);
                    Set<String> hashes = new HashSet<>();
                    String lastHash = null;
                    int i;
                    for (i = 0; i < ITERATIONS; i++) {
                        int alive = cube.countAlive();
                        if (alive < 1) {
                            return Triplet.of(v, Couple.of(hashes.size(), i), DEAD);
                        }
                        if (alive > 400) {
                            return Triplet.of(v, Couple.of(hashes.size(), i), OVERFLOW);
                        }
                        String hash = cube.toHashString();
                        if (hash.equals(lastHash)) {
                            return Triplet.of(v, Couple.of(hashes.size(), i), STATIC);
                        } else {
                            lastHash = hash;
                        }
                        int lastHashSize = hashes.size();
                        hashes.add(hash);
                        int hashSize = hashes.size();
                        if (lastHashSize == hashSize) {
                            return Triplet.of(v, Couple.of(hashes.size(), i), REPEAT);
                        }
                        cube.iterate();
                    }
                    return Triplet.of(v, Couple.of(hashes.size(), i), OVERFLOW);
                }).collect(toList());
        System.out.println("results");
        results.stream()
                .filter(x -> !OVERFLOW.equals(x._3))
//                .filter(x -> x._2._1 > 1)
//                .filter(x -> x._2._1 != x._2._2)/
                .forEach(x -> {
                    System.out.println(Arrays.toString(x._1) + " " + x._2._1 + " " + x._2._2 + " " + x._3);
                });

    }
}

enum State {
    DEAD,
    OVERFLOW,
    REPEAT,
    STATIC
}