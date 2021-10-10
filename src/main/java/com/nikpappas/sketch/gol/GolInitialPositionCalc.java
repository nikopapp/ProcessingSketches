package com.nikpappas.sketch.gol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public class GolInitialPositionCalc {
    public static void main(String[] args) {
        GolInitialPositionCalc app = new GolInitialPositionCalc();
        List<String[]> combinations = app.generateCombinations(4, 4);
    }

    List<String[]> generateCombinations(int x, int y) {
        List<String> lineCombi = generateLineCombinations(x);
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

    List<String[]> generateCombination(List<String> lineCombinations, List<String[]> build, String[] s, int y) {
        if (y == 0) {
            return singletonList(s);
        }
        lineCombinations.forEach(lComb -> {
            String[] newArr = Arrays.copyOf(s, s.length );
            newArr[y-1] = lComb;
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


}
