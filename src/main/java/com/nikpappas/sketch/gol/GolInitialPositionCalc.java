package com.nikpappas.sketch.gol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class GolInitialPositionCalc {
    public static void main(String[] args) {
        GolInitialPositionCalc app = new GolInitialPositionCalc();
        List<String[][]> combinations = app.generateCombinations(3, 3, 2);
//        combinations.forEach(System.out::println);
        System.out.println(combinations.size());
    }


    List<String[][]> generateCombinations(int x, int y, int z) {
        List<String[]> lineCombi = generateCombinations(x, y);
        System.out.println("2d combinations generated");
        List<String[][]> toBuild = new ArrayList<>();
        List<String[][]> res = generateCombination(lineCombi, toBuild, new String[z][], z);
        res.forEach(c -> {
            System.out.println("===================");
            for (String[] sarr : c) {
                System.out.println(Arrays.toString(sarr));
            }
        });
        return res;
    }

    List<String[]> generateCombinations(int x, int y) {
        List<String> lineCombi = generateLineCombinations(x);
        System.out.println("1d combinations generated");
        List<String[]> res = generateCombination(lineCombi, new ArrayList<>(), new String[y], y);
        res.forEach(c -> {
            System.out.println("===================");
            System.out.println(Arrays.toString(c));
        });
        return res;
    }

    List<String> generateLineCombinations(int x) {
        return generateLineCombination(new ArrayList<>(), "", x);
    }

    List<String[][]> generateCombination(List<String[]> lineCombinations, List<String[][]> build, String[][] s, int z) {
        if (z == 0) {
            return singletonList(s);
        }
        lineCombinations.forEach(lComb -> {
            String[][] newArr = Arrays.copyOf(s, s.length);
            newArr[z - 1] = lComb;
            System.out.println(z);
            build.addAll(generateCombination(lineCombinations, new ArrayList<>(), newArr, z - 1));
        });

        return build;

    }

    List<String[]> generateCombination(List<String> lineCombinations, List<String[]> build, String[] s, int y) {
        if (y == 0) {
            return singletonList(s);
        }
        lineCombinations.forEach(lComb -> {
            String[] newArr = Arrays.copyOf(s, s.length);
            newArr[y - 1] = lComb;
            build.addAll(generateCombination(lineCombinations, new ArrayList<>(), newArr, y - 1));
        });
        return build;

    }

    List<String> generateLineCombination(List<String> build, String s, int x) {

        if (x == 0) {
            return singletonList(s);
        }

        String zero = s + ".";
        String one = s + "#";
        build.addAll(generateLineCombination(new ArrayList<>(), zero, x - 1));
        build.addAll(generateLineCombination(new ArrayList<>(), one, x - 1));

        return build;
    }


    public List<String[][]> generateRandomCombinations(int n, int x, int y, int z) {
        String sample = IntStream.range(0, z).mapToObj(__ -> "#").collect(joining(""));
        return IntStream.range(0, n).mapToObj(__ -> {
            String[][] a = new String[y][];
            for (int i = 0; i < y; i++) {
                a[i] = new String[x];
                for (int j = 0; j < x; j++) {
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < sample.length(); k++) {
                        String cur = Math.random() > 0.5 ? "#" : ".";
                        sb.append(cur);

                    }
                    a[i][j] = sb.toString();

                }
            }
            return a;
        }).collect(toList());


    }

}
