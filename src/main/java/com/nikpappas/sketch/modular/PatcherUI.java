package com.nikpappas.sketch.modular;

import com.nikpappas.utils.collection.Couple;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class PatcherUI extends PApplet {

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }

    private static final float LABEL_OFFSET = 20;

    private static final Couple<Float> HOLE_BEHIND_DESK = Couple.of(-50f, 0f);
    private static final Couple<Float> HOLE_MIDDLE = Couple.of(50f, 35f);
    private static final Couple<Float> HOLE_RIGHT_WALL = Couple.of(264f, 0f);
    private static final Couple<Float> HOLE_LEFT_WALL = Couple.of(-300f, 0f);

    private int movingModule = -1;
    private Map<Integer, PatcherModule> modules = new HashMap<>();
    private Map<Integer, PatcherConnection> connections = new HashMap<>();

    private final int MIXER = 0;
    private final int TURNTABLE = 1;
    private final int PC = 2;
    private final int SPKR_L = 3;
    private final int SPKR_R = 4;
    private final int SYNTH = 5;
    private final int MONITOR = 6;
    private final int MULTI_4 = 7;
    private final int MULTI_6 = 8;
    private final int LAPTOP = 9;
    private final int BLUETOOTH = 10;
    private final int HARD_DRIVES = 11;
    private final int RAW_AUDIO_CABLE = 12;


    {
        modules.put(MIXER, PatcherModule.of("Mixer", 250, 45));
        modules.put(TURNTABLE, PatcherModule.of("Turntable", 264, -35));
        modules.put(PC, PatcherModule.of("PC", -280, 100));
        modules.put(SPKR_L, PatcherModule.of("SpeakerL", 185, -142));
        modules.put(SPKR_R, PatcherModule.of("SpeakerR", 280, -250));
        modules.put(SYNTH, PatcherModule.of("Synth", -130, -60));
        modules.put(MONITOR, PatcherModule.of("Monitor", -280, -60));
        modules.put(MULTI_4, PatcherModule.of("Multi4", -20, 30));
        modules.put(MULTI_6, PatcherModule.of("Multi6", 210, 80));
        modules.put(LAPTOP, PatcherModule.of("Laptop", 0, -60));
        modules.put(BLUETOOTH, PatcherModule.of("Bluetooth", 170, 30));
        modules.put(HARD_DRIVES, PatcherModule.of("HDDs", 170, 90));
        modules.put(RAW_AUDIO_CABLE, PatcherModule.of("AudioIn", -80, -60));

        putConnection(PatcherConnection.of(MIXER, TURNTABLE), asList(HOLE_RIGHT_WALL));
        putConnection(PatcherConnection.of(MIXER, PC), asList(HOLE_MIDDLE));
        putConnection(PatcherConnection.of(MIXER, BLUETOOTH));
        putConnection(PatcherConnection.of(MIXER, RAW_AUDIO_CABLE), asList(HOLE_MIDDLE, HOLE_BEHIND_DESK));
        putConnection(PatcherConnection.of(MIXER, SPKR_L), asList(HOLE_RIGHT_WALL));
        putConnection(PatcherConnection.of(SPKR_L, SPKR_R));
        putConnection(PatcherConnection.of(MONITOR, PC), asList(HOLE_LEFT_WALL));
        putConnection(PatcherConnection.of(SYNTH, PC), asList(HOLE_LEFT_WALL));
        putConnection(PatcherConnection.of(HARD_DRIVES, PC), asList(HOLE_MIDDLE));
        putConnection(PatcherConnection.of(MONITOR, LAPTOP), asList(Couple.of(-30f, -80f)));
        // POWER
        putConnection(PatcherConnection.of(MULTI_4, PC));
        putConnection(PatcherConnection.of(MULTI_4, MONITOR), asList(HOLE_LEFT_WALL));
        putConnection(PatcherConnection.of(MULTI_4, BLUETOOTH), asList(HOLE_MIDDLE));
        putConnection(PatcherConnection.of(MULTI_4, LAPTOP), asList(HOLE_BEHIND_DESK));
        putConnection(PatcherConnection.of(MULTI_6, MIXER));
        putConnection(PatcherConnection.of(MULTI_6, HARD_DRIVES));
        putConnection(PatcherConnection.of(MULTI_6, TURNTABLE), asList(HOLE_RIGHT_WALL));
    }


    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
    }

    @Override
    public void draw() {
        background(33, 33, 33);
        pushMatrix();
        translate(width / 2, height / 2);

        stroke(255, 255, 255, 20f);
        strokeWeight(2f);
        connections.forEach((idx, conn) -> {
            PatcherModule module1 = modules.get(conn.id1);
            PatcherModule module2 = modules.get(conn.id2);
            List<Couple<Float>> points = new ArrayList<>();
            points.add(Couple.of(module1.x, module1.y));
            points.addAll(conn.pins.stream()
                    .map(x -> Couple.of(x._1 + random(5), x._2 + random(5)))
                    .collect(toList()));
            points.add(Couple.of(module2.x, module2.y));

            for (int i = 0; i < points.size() - 1; i++) {
                line(points.get(i)._1, points.get(i)._2,
                        points.get(i + 1)._1, points.get(i + 1)._2);
            }
        });

        strokeWeight(2f);
        stroke(100);
        modules.forEach((i, module) -> {
            if (i == movingModule) fill(100);
            else fill(200);
            ellipse(module.x, module.y, module.size, module.size);
            text(module.name, module.x - LABEL_OFFSET, module.y - LABEL_OFFSET);
        });

        popMatrix();
        text(translateMouseClick(mouseX, mouseY).toString(), mouseX, mouseY);
        delay(100);
    }


    @Override
    public void mouseDragged(MouseEvent me) {
        Couple<Integer> drag = translateMouseClick(me.getX(), me.getY());

        if (movingModule == -1) {
            OptionalInt p = modules.entrySet().stream().filter(e -> {
                System.out.println(e.getValue().x < drag._1 && drag._1 < (e.getValue().x + e.getValue().size));
                return (e.getValue().x - e.getValue().size / 2) < drag._1 && drag._1 < (e.getValue().x + e.getValue().size / 2) &&
                        (e.getValue().y - e.getValue().size / 2) < drag._2 && drag._2 < (e.getValue().y + e.getValue().size / 2);
            }).mapToInt(Map.Entry::getKey).findAny();
            movingModule = p.orElse(-1);
        }

        if (movingModule > -1) {
            modules.get(movingModule).x = drag._1;
            modules.get(movingModule).y = drag._2;
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Optional<PatcherModule> module = Optional.ofNullable(modules.get(movingModule));
        module.ifPresent(m -> {
            Couple<Integer> release = translateMouseClick(me.getX(), me.getY());
            m.x = release._1;
            m.y = release._2;
        });
        movingModule = -1;
    }

    private Couple<Integer> translateMouseClick(int x, int y) {
        return Couple.of(x - width / 2, y - height / 2);
    }

    private void putConnection(PatcherConnection c) {
        putConnection(c, emptyList());
    }

    private void putConnection(PatcherConnection c, List<Couple<Float>> pins) {
        int max = connections.keySet().stream().mapToInt(x -> x).max().orElse(0);
        c.pins.addAll(pins);
        connections.put(max + 1, c);
    }

}
