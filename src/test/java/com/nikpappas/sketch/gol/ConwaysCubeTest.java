package com.nikpappas.sketch.gol;

import com.nikpappas.utils.collection.Trio;
import com.nikpappas.utils.collection.Triplet;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import com.nikpappas.utils.collection.Pair;


import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
        cube.put(0, 3, -5, '.');
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
    public Stream<DynamicTest> testToHashΠθττινγ() {
        Stream<Pair<List<Triplet<Integer, Integer, Character>>, String>> input = Stream.of(
                Pair.of(asList(
                        Triplet.of(0, 0, '#')
                ), "#"),
                Pair.of(asList(
                        Triplet.of(-1, 0, '#')
                ), "...#...."),
                Pair.of(asList(
                        Triplet.of(0, -1, '#')
                ), ".....#.."),
                Pair.of(asList(
                        Triplet.of(-1, -1, '#')
                ), ".#......"),
                Pair.of(asList(
                        Triplet.of(1, 1, '#')
                ), "......#."),
                Pair.of(asList(
                        Triplet.of(1, 2, '#'),
                        Triplet.of(2, 2, '#')
                ), "...............#........#..")
        );
        return DynamicTest.stream(input, x -> "2D hash test -> " + x._2, (c) -> {
            ConwaysCube cube = new ConwaysCube();
            c._1.forEach(i -> {
                cube.put(i._1, i._2, 0, i._3);
            });

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

    @Test
    public void testToStrinArrayEmpty() {
        ConwaysCube cube = new ConwaysCube();
        String[][] actual = cube.toStringArray();
        String[][] expected = new String[][]{
                {}
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testToStrinArray() {
        ConwaysCube cube = new ConwaysCube();
        cube.put(0, 0, 0, '#');
        String[][] actual = cube.toStringArray();
        String[][] expected = new String[][]{
                {"#"}
        };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testToStrinArrayOffset() {
        ConwaysCube cube = new ConwaysCube();
        cube.put(1, 2, 3, '#');
        String[][] actual = cube.toStringArray();
        String[][] expected = new String[][]{
                {"#"},
        };
        assertArrayEquals(expected, actual);
    }

    @TestFactory
    public Stream<DynamicTest> testToStrinArrayOffsetComplex() {
        Stream<Pair<List<Trio<Integer>>, String[][]>> input = Stream.of(
                Pair.of(asList(
                                Trio.of(1, 2, 3),
                                Trio.of(1, 2, 1)
                        ),
                        new String[][]{
                                {"#.#"},
                        }
                ),
                // Test 2
                Pair.of(asList(
                                Trio.of(2, 2, 1),
                                Trio.of(1, 2, 1)
                        ),
                        new String[][]{
                                {"#"},
                                {"#"},
                        }
                ),
                // Test 3
                Pair.of(asList(
                                Trio.of(1, 2, 3),
                                Trio.of(3, 2, 1)
                        ),
                        new String[][]{
                                {"..#"},
                                {"..."},
                                {"#.."},
                        }
                )

        );

        return DynamicTest.stream(input, x -> "Complex toStringArray -> " + x._2, (c) -> {
            ConwaysCube cube = new ConwaysCube();
            c._1.forEach(coord -> cube.put(coord._1, coord._2, coord._3, '#'));
            String[][] actual = cube.toStringArray();
            String[][] expected = c._2;
            assertArrayEquals(expected, actual);
        });
    }

}