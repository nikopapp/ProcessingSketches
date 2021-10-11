package com.nikpappas.sketch.gol;

import com.nikpappas.processing.core.Pair;
import com.nikpappas.processing.core.Trio;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ConwaysCubeTest {
    @Test
    public void test1() {

        String[][] cubeInit = {
                {
                        "...",
                        "...",
                        "..#"
                },
                {
                        ".#.",
                        "#..",
                        "..#"
                }};

        ConwaysCube cube = ConwaysCube.of(cubeInit);
        assertEquals(2, cube.getAbsExtent());

    }

    @Test
    public void minExtent() {
        ConwaysCube cube = new ConwaysCube();
        cube.put(0,3,-5, '.');
        assertEquals(-5, cube.getMinExtent());
    }

    @Test
    public void testCoords() {
        String[][] cubeInit = {
                {
                        "...",
                        "...",
                        "..#"
                },
                {
                        ".#.",
                        "#..",
                        "..#"
                }};

        ConwaysCube cube = ConwaysCube.of(cubeInit);
        Set<Trio<Integer>> coords = cube.getCoords();
        coords.forEach(x -> {
            assertEquals(cubeInit[x._1][x._2].charAt(x._3), cube.get(x._1, x._2, x._3));
        });


    }

    @TestFactory
    public Stream<DynamicTest> testToHash2d() {
        Stream<Pair<String[], String>> input = Stream.of(
                Pair.of(new String[]{"#"}, "#"),
                Pair.of(new String[]{"#", "#"}, "#.#....."),
                Pair.of(new String[]{".", "."}, "........"),
                Pair.of(new String[]{".", "#"}, "..#....."),
                Pair.of(new String[]{"#", "."}, "#......."),
                Pair.of(new String[]{"#..", "#..", "#.."}, "#..#..#....................")
        );
        return DynamicTest.stream(input, x -> "2D hash test -> " + x._2, (c) -> {
            System.out.println(Arrays.toString(c._1));
            ConwaysCube cube = ConwaysCube.of(c._1);
            assertEquals(c._2, cube.toHashString());
        });


    }

    @TestFactory
    public Stream<DynamicTest> testToHash3d() {
        Stream<Pair<String[][], String>> input = Stream.of(
                Pair.of(new String[][]{{"#"}}, "#"),
                Pair.of(new String[][]{
                        {"#"},
                        {"#"},
                }, "#...#..."),
                Pair.of(new String[][]{
                        {"##"},
                        {"#."},
                }, "##..#...")
        );
        return DynamicTest.stream(input, x -> "3D hash test -> " + x._2, (c) -> {
            System.out.println(Arrays.toString(c._1));
            ConwaysCube cube = ConwaysCube.of(c._1);
            assertEquals(c._2, cube.toHashString());
        });


    }

    @TestFactory
    public Stream<DynamicTest> testToHash3dBounding() {
        Stream<Pair<String[][], String>> input = Stream.of(
                Pair.of(new String[][]{{"#"}}, "#"),
                Pair.of(new String[][]{
                        {"#"},
                        {"#"},
                }, "#........#"),
                Pair.of(new String[][]{
                        {"##"},
                        {"#."},
                }, "##.......#")
        );
        return DynamicTest.stream(input, x -> "3D hash test -> " + x._2, (c) -> {
            System.out.println(Arrays.toString(c._1));
            ConwaysCube cube = ConwaysCube.of(c._1);
            assertEquals(c._2, cube.toHashStringBounding());
        });


    }

    @Test
    public void testCoordsValues() {
        String[][] cubeInit = {
                {
                        "...",
                        "...",
                        "..#"
                },
                {
                        ".#.",
                        "#..",
                        "..#"
                }};

        ConwaysCube cube = ConwaysCube.of(cubeInit);
        Set<Trio<Integer>> coords = cube.getCoords();
        coords.forEach(x -> {
            assertEquals(cubeInit[x._1][x._2].charAt(x._3), cube.get(x._1, x._2, x._3));
        });


    }

}