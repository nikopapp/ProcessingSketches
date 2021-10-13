package com.nikpappas.sketch;

import com.nikpappas.processing.core.Couple;
import com.nikpappas.processing.core.Pair;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class DataVisualiserMap extends PApplet {

    public static final String DATA_DIR = "/Users/nikos/DataSets/seismic/usgs/coordinatesOfEarthquakes2020AndClusterData/";
    List<List<Float>> coordinates;
    List<List<Float>> centres;
    List<Pair<String, List<Float>>> places;

    double maxX = 180;
    double maxY = 90;
    private PImage worldMap;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }


    @Override
    public void settings() {
        fullScreen();
        worldMap = loadImage("/Users/nikos/Projects/ProcessingSketches/assets/seismin/world-map-cylindrical.jpg");
        worldMap.filter(PConstants.GRAY);
        try {
            Path file = Files.walk(Paths.get(DATA_DIR + "coordinatesOfEarthquakes2020/"))
                    .filter(f -> f.toFile().isFile() && f.toFile().getName().endsWith("csv")).findAny().orElseThrow(RuntimeException::new);
            System.out.println("using file:" + file);
            coordinates = Files.readAllLines(file).stream()
                    .map(x -> Arrays.stream(x.split(","))
                            .map(t -> parseFloat(t.trim())).collect(toList()))
                    .collect(toList());

//            maxX = coordinates.stream().mapToDouble(x -> abs(x.get(0))).max().orElseThrow(() -> new RuntimeException());
//            maxY = coordinates.stream().mapToDouble(x -> abs(x.get(1))).max().orElseThrow(() -> new RuntimeException());
            centres = Files.readAllLines(
                            Paths.get(DATA_DIR + "clusters.txt")).stream()
                    .map(x -> Arrays.stream(x.replace("[", "").replace("]", "").split(","))
                            .map(t -> parseFloat(t.trim())).collect(toList()))
                    .collect(toList());
            System.out.println("Loaded");
            places = asList(
                    Pair.of("Greece", asList(21.8243f, 39.0742f)),
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

        stroke(120, 0, 110, 200);

        coordinates.forEach(c -> {
            Couple<Float> coords = mapCoordinates(c);
            float xc = coords._1;
            float yc = coords._2;

            point(xc, yc);
        });

        fill(250, 140, 139);

        centres.forEach(c -> {
            Couple<Float> coords = mapCoordinates(c);
            float xc = coords._1;
            float yc = coords._2;
            ellipse(xc, yc, 10, 10);
        });
        fill(45, 240, 239);
        places.forEach(c -> {
            Couple<Float> coords = mapCoordinates(c._2);

            float xc = coords._1;
            float yc = coords._2;
            text(c._1, xc, yc + 30);
            ellipse(xc, yc, 15f, 15f);
        });
        delay(1000);
        System.out.println("drwa");
        popMatrix();
    }

    Couple<Float> mapCoordinates(List<Float> c) {
//        double xc = c.get(0) * (width / 2) / maxX;
//        double yc = -c.get(1)*(abs(c.get(1)*.1f)) * (height / 2) / maxY;
//        return Couple.of((float) xc, (float) yc);
        double xc = c.get(0) * (width / 2) / maxX;
        double yc = -Math.sin(deg2rad(c.get(1)))* (height / 2) ;
        return Couple.of((float) xc, (float) yc);
    }

    float deg2rad(float deg) {
        return PI * deg/180f;
    }
}
