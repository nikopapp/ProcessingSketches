package com.nikpappas.sketch.gol;

import processing.core.PApplet;

import static com.nikpappas.processing.benchmark.Timing.timed;

public class GameOfLife3D extends PApplet {

    private ConwaysCube cube = new ConwaysCube();
//[.##, .##, ...] 1 1 REPEAT
//[##., ##., ...] 1 1 REPEAT
//[#.., .##, .#.] 14 14 REPEAT
//[#.., ..#, .##] 10 10 REPEAT
//[..., .##, .##] 1 1 REPEAT
//[##., .#., #..] 8 8 REPEAT
//[##., ##., #..] 15 15 REPEAT
//[..., ##., ##.] 1 1 REPEAT
//[###, ###, ##.] 7 7 REPEAT

    float rotation = 0;
    int firsttimestamp = millis();
    float timestamp = 0f;
    int renderTime = 0;
    boolean pause = true;
    float scaleC = 20;
    float colourScale = 20;
    private static final int BACKGROUND = 200;

    private int iteration = 0;


    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    @Override
    public void settings() {
//        fullScreen(P3D);
        fullScreen(P3D, 1);
//        size(900, 800, P3D);
//        char[][] init = new char[][]{
//                {'.', '.', '.', '#', '.', '.', '#', '.'},
//                {'#', '.', '.', '#', '.', '.', '.', '#'},
//                {'.', '.', '.', '.', '.', '#', '#', '#'},
//                {'#', '#', '.', '.', '.', '.', '#', '#'},
//                {'.', '.', '.', '.', '.', '.', '#', '#'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '#', '.', '.', '.', '.', '.', '.'},
//                {'#', '#', '.', '.', '.', '#', '.', '.'},
//        };
//        char[][] init = new char[][]{
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '#', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '#', '#', '.', '.'},
//                {'.', '.', '.', '.', '#', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//        };
////        Stars still after 4
//        char[][] init = new char[][]{
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '#', '.', '#', '.', '.', '.'},
//                {'.', '.', '.', '.', '#', '#', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '#', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//        };
        char[][] init = new char[][]{
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '#', '#', '#', '#', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '#', '#', '#', '#', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'},
        };

        // Stays still after 6 positions
//        char[][] init = new char[][]{
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '#', '#', '#', '.', '.', '.'},
//                {'.', '.', '#', '#', '#', '.', '.', '.'},
//                {'.', '.', '#', '#', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.', '.', '.'},
//        };

        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[i].length; j++) {
                cube.put(i - init.length / 2, j - init[i].length / 2, 0, init[i][j]);
            }
        }
//        String[] cubeInit = {
//                "...#",
//                "#..#",
//                "##.#"
//        };
        String[][] cubeInit = {
                {
                        "...",
                        ".#.",
                        "...",
                },
                {
                        ".#.",
                        "#.#",
                        ".#."
                },
                {
                        "...",
                        ".#.",
                        "...",
                },
        };
        cube = ConwaysCube.of(cubeInit);
    }

    @Override
    public void setup() {
        background(BACKGROUND);
    }

    @Override
    public void draw() {
        background(BACKGROUND);
        pushMatrix();
        translate(width / 2, height / 2, 0);

        int extent = cube.getExtent();
        float orbitRadius = mouseX / 2 + 30 * extent;
        float ypos = (mouseY - (height / 2)) / 3 * extent;
        float xpos = cos(radians(rotation)) * orbitRadius;
        float zpos = sin(radians(rotation)) * orbitRadius;

        camera(xpos, ypos, zpos, 0, 0, 0, 0, -1, 0);

        timed(() -> drawCubes(extent), "draw");
        popMatrix();
        int curIteration = millis() - firsttimestamp;
        if (curIteration - timestamp > 1000 && !pause) {
            timestamp = curIteration;
            timed(cube::iterate, "Iterate " + iteration++);
        }
        text(iteration, 30, 30);
        int delayTime = 30 - (curIteration - renderTime);
        renderTime = curIteration;
        if (delayTime > 0) {
            delay(delayTime);
        } else {
            delay(6);
        }
        rotation += 0.2;
    }


    private void drawCubes(int extent) {
        cube.getCoords().stream()
                // Ommit the center ones
//                .filter(x -> abs(x._1) + abs(x._2) + abs(x._3) > extent)
                .forEach(t -> {
                    if (cube.isAlive(t._1, t._2, t._3)) {
                        pushMatrix();
                        translate(t._1 * scaleC, t._2 * scaleC, t._3 * scaleC);
                        stroke(abs(t._1) * 4 * colourScale / 5, abs(t._2) * 4 * colourScale / 5, abs(t._3) * 4 * colourScale / 5);
                        fill(abs(t._1) * colourScale, abs(t._2) * colourScale, abs(t._3) * colourScale);
                        box(10);
                        popMatrix();
                    } else {
//                box(2);
                    }
                });

    }

    @Override
    public void keyPressed() {
        if (key == ' ') { // On/off of pause
            pause = !pause;
        }
        if (key == 's') {
            saveFrame("GOL###.png");
        }
    }
}
