package com.nikpappas.sketch;

import com.nikpappas.processing.core.Couple;
import com.nikpappas.processing.core.Pair;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class DataVisualiserMap extends PApplet {

    public static final String DATA_DIR = "/Users/nikos/DataSets/seismic/usgs/coordinatesOfEarthquakes2020AndClusterData/";
    List<List<Float>> coordinates;
    List<List<Float>> centres;
    List<Pair<String, List<Float>>> places;

    double maxX = 180;
    private PImage worldMap;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }


    @Override
    public void settings() {
//        fullScreen();
        float ratio = 2 / 1.1f;
        int size = 900;
        int sizeX = Math.round(size * ratio);
        size(sizeX, size);
        worldMap = loadImage("/Users/nikos/Projects/ProcessingSketches/assets/seismin/world-map-cylindrical.jpg");
        for (int i = 0; i < worldMap.width; i++) {
            for (int j = 0; j < worldMap.height; j++) {
                int r = (worldMap.get(i, j) & 0x00ff0000) >>> 16;
                int g = (worldMap.get(i, j) & 0x0000ff00) >>> 8;
                int b = (worldMap.get(i, j) & 0x000000ff);
                int a = (worldMap.get(i, j) & 0xff000000) >>> 24;
                int avg = (r + g + b) / 3;

                r = (2 * avg + r) / 3;
                g = (2 * avg + g) / 3;
                b = (2 * avg + b) / 3;

                int c = (a << 24) + (r << 16) + (g << 8) + b;

                worldMap.set(i, j, c);
            }
        }
//        worldMap.filter(PConstants.GRAY, 0.5f);
        try {
            Path file = Files.walk(Paths.get(DATA_DIR + "coordinatesOfEarthquakes2020/"))
                    .filter(f -> f.toFile().isFile() && f.toFile().getName().endsWith("csv")).findAny().orElseThrow(RuntimeException::new);
            System.out.println("using file:" + file);
            coordinates = Files.readAllLines(file).stream()
                    .map(x -> Arrays.stream(x.split(","))
                            .map(t -> parseFloat(t.trim())).collect(toList()))
//                    .filter(x -> x.get(2) > 3)
                    .sorted(comparing(x -> x.get(2)))
                    .collect(toList());

            centres = Files.readAllLines(
                            Paths.get(DATA_DIR + "clusters.txt")).stream()
                    .map(x -> Arrays.stream(x.replace("[", "").replace("]", "").split(","))
                            .map(t -> parseFloat(t.trim())).collect(toList()))
                    .collect(toList());
            System.out.println("Loaded");
            places = asList(
//                    40.6401° N, 22.9444° E
                    Pair.of("Thessaloniki", asList(22.9444f, 40.6401f)),
                    // 51.5074° N, 0.1278° W
                    Pair.of("London", asList(-0.1278f, 51.5074f)),
                    // 1.8312° S, 78.1834° W
                    Pair.of("Equador", asList(-78.1834f, -1.8312f)),
                    Pair.of("Ney York", asList(-74.0060f, 40.7128f))
            );
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void setup() {
        color(0, 0.4f);
        background(1, 1, 1);

    }

    @Override
    public void draw() {
        background(256, 256, 256);
        image(worldMap, -5, -5, width + 10, height + 10);
        pushMatrix();
        translate((width / 2), height / 2);

        stroke(230, 150, 170, 40);

        coordinates.forEach(c -> {
            Couple<Float> coords = mapCoordinates(c);
            float xc = coords._1;
            float yc = coords._2;

            float sizeMag = (float) (Math.pow(c.get(2), 3)) / 4;
            float alphaMag = (float) (2 * Math.pow(c.get(2), 2)) / 5;
            int col = color(100 + (alphaMag * 10), 110 - alphaMag, 140 - alphaMag, alphaMag);
            fill(col);


            ellipse(xc, yc, sizeMag, sizeMag);
        });

        stroke(220, 200, 109);
        fill(110, 80, 100);

        centres.forEach(c -> {
            Couple<Float> coords = mapCoordinates(c);
            float xc = coords._1;
            float yc = coords._2;
            ellipse(xc, yc, 10, 10);
        });
        places.forEach(c -> {
            fill(45, 200, 239);
            Couple<Float> coords = mapCoordinates(c._2);

            float xc = coords._1;
            float yc = coords._2;
            stroke(255);
            ellipse(xc, yc, 15f, 15f);
            fill(255);
            text(c._1, xc, yc + 30);
        });
        delay(300);
        System.out.println("drwa");
        popMatrix();
        Couple<Float> mousCoord = reverseCoordinates(mouseX, mouseY);

        text(format("%.2f, %.2f", mousCoord._1, mousCoord._2), 30, 30);
    }

    Couple<Float> mapCoordinates(List<Float> c) {
        double xc = c.get(0) * (width / 2) / maxX;
        double yc = -Math.sin(deg2rad(c.get(1))) * (height / 2);
        return Couple.of((float) xc, (float) yc);
    }

    Couple<Float> reverseCoordinates(int x, int y) {
        double xc = x * maxX / (width / 2) - maxX;

        double yc = -rad2deg((float) Math.asin(((float) y - height / 2) / (height / 2)));
        return Couple.of((float) xc, (float) yc);
    }

    float deg2rad(float deg) {
        return PI * deg / 180f;
    }

    float rad2deg(float rad) {
        return 180f * rad / PI;
    }
}
