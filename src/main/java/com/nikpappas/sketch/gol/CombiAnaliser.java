package com.nikpappas.sketch.gol;


import com.nikpappas.utils.collection.Couple;
import com.nikpappas.utils.collection.Pair;
import com.nikpappas.utils.collection.Triplet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.nikpappas.sketch.gol.State.*;
import static java.util.stream.Collectors.toList;


public class CombiAnaliser {
    public static final int ITERATIONS = 10;

    public static void main(String[] args) {
        GolInitialPositionCalc combiCalc = new GolInitialPositionCalc();

//        List<String[][]> combinations = combiCalc.generateRandomCombinations(1000, 5, 5, 5);
////        List<String[]> combinations = combiCalc.generateCombinations(3, 3);
//        Map<Integer, String[][]> withIndex = new TreeMap<>();
//        for (int i = 0; i < combinations.size(); i++) {
//            withIndex.put(i, combinations.get(i));
//        }

        List<Triplet<String[][], Couple<Integer>, Pair<State, String[][]>>> results = IntStream.range(0, 1000).mapToObj(x -> combiCalc.generateRandomCombinations(10, 6, 6, 6))
                .flatMap(List::stream).map(CombiAnaliser::map2Result)
                .filter(x -> REPEAT.equals(x._3._1))
                .limit(10)
                .collect(toList());


//        System.out.println("# combi:" + combinations.size());
//        List<Triplet<String[][], Couple<Integer>, Pair<State, String[][]>>> results = withIndex.entrySet().stream()
//                .parallel()
//                .map(x -> map2Result(x.getValue(), x.getKey()))
//                .collect(toList());

        System.out.println("results");
        results.stream()
                .filter(x -> !OVERFLOW.equals(x._3))
                .filter(x -> !DEAD.equals(x._3))
//                .filter(x -> REPEAT.equals(x._3))
//                .filter(x -> x._2._1 > 1)
//                .filter(x -> x._2._1 != x._2._2)/
                .forEach(x -> {
                    System.out.println(
                            array2dToString(x._1, " -- ") +
                                    " " + x._2._1 + " " + x._2._2 + " " + x._3._1 +
                                    array2dToString(x._3._2, " -- "));
                });

    }

    public static Triplet<String[][], Couple<Integer>, Pair<State, String[][]>> map2Result(String[][] arr) {
        return map2Result(arr, 3);
    }

    public static Triplet<String[][], Couple<Integer>, Pair<State, String[][]>> map2Result(String[][] arr, int index) {
        String[][] v = arr;
        if (index % 10 == 0) {
            System.out.println(index);
        }
        ConwaysCube cube = ConwaysCube.of(v);
        Set<String> hashes = new HashSet<>();
        String lastHash = null;
        int i;
        for (i = 0; i < ITERATIONS; i++) {
            int alive = cube.countAlive();
            if (alive < 1) {
                return Triplet.of(v, Couple.of(hashes.size(), i), Pair.of(DEAD, cube.toStringArray()));
            }
            if (alive > 400) {
                return Triplet.of(v, Couple.of(hashes.size(), i), Pair.of(OVERFLOW, cube.toStringArray()));
            }
            String hash = cube.toHashString();
            if (hash.equals(lastHash)) {
                return Triplet.of(v, Couple.of(hashes.size(), i), Pair.of(STATIC, cube.toStringArray()));
            } else {
                lastHash = hash;
            }
            int lastHashSize = hashes.size();
            hashes.add(hash);
            int hashSize = hashes.size();
            if (lastHashSize == hashSize) {
                return Triplet.of(v, Couple.of(hashes.size(), i), Pair.of(REPEAT, cube.toStringArray()));
            }
            cube.iterate();
        }
        return Triplet.of(v, Couple.of(hashes.size(), i), Pair.of(OVERFLOW_ITERATION, cube.toStringArray()));

    }

    public static String array2dToString(String[][] arr, String delimiter) {
        return Arrays.stream(arr).map(Arrays::toString)
                .collect(Collectors.joining(delimiter));
    }

    private static Triplet<String[][], Couple<Integer>, Pair<State, String[][]>> map2Result(Map.Entry<Integer, String[][]> combination) {
        return map2Result(combination.getValue(), combination.getKey());
    }
}


enum State {
    DEAD,
    OVERFLOW,
    OVERFLOW_ITERATION,
    REPEAT,
    STATIC
}