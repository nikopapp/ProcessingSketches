package com.nikpappas.sketch;

import com.nikpappas.processing.core.Triplet;
import processing.core.PApplet;
import processing.sound.AudioIn;
import processing.sound.FFT;
import processing.sound.SoundFile;

import java.util.ArrayDeque;
import java.util.Deque;


public class Beats2Hoops extends PApplet {
    /**
     * This sketch shows how to use the FFT class to analyze a stream
     * of sound. Change the number of bands to get more spectral bands
     * (at the expense of more coarse-grained time resolution of the spectrum).
     */


    // Declare the sound source and FFT analyzer variables
    private SoundFile sample;
    private FFT fft;

    private AudioIn in;
    private Deque<Triplet<Float, Float, Float>> buffer;

    // Define how many FFT bands to use (this needs to be a power of two)
    private int bands = 32;

    private final int MIN_RADIUS = 20;

    // Define a smoothing factor which determines how much the spectrums of consecutive
// points in time should be combined to create a smoother visualisation of the spectrum.
// A smoothing factor of 1.0 means no smoothing (only the data from the newest analysis
// is rendered), decrease the factor down towards 0.0 to have the visualisation update
// more slowly, which is easier on the eye.
    private float smoothingFactor = 0.2f;

    // Create a vector to store the smoothed spectrum data in
    private float[] sum = new float[bands];

    // Variables for drawing the spectrum:

    private long timestamp;

    public static void main(String[] args) {
        PApplet.main(Thread.currentThread().getStackTrace()[1].getClassName());
    }


    @Override
    public void settings() {

        timestamp = 0;
        buffer = new ArrayDeque<>();
        size(900, 600, P3D);


        // Load and play a soundfile and loop it.
        //sample = new SoundFile(this, "/Users/nikos/Projects/WaveformVisualiser/samples/Crop/5K-33uF.aif");
        //sample = new SoundFile(this, "/Users/nikos/Music/SonicPi/Πολυτελής ΕγκλεισμόςFFFD_v1.aif");
        sample = new SoundFile(this, "/Users/nikos/Music/SonicPi/MelodyMoody.wav");
        //sample = new SoundFile(this, "beat.aiff");
        sample.loop();
//        in = new AudioIn(this, 0);

        // start the Audio Input
//        in.start();


        // Create the FFT analyzer and connect the playing soundfile to it.
        fft = new FFT(this, bands);
        fft.input(sample);
//        fft.input(in);

    }

    @Override
    public void draw() {
        // Set background color, noStroke and fill color
        background(17, 15, 16);
        noFill();
        // Perform the analysis
        fft.analyze();

        for (int i = 0; i < bands; i++) {
            // Smooth the FFT spectrum data by smoothing factor
            sum[i] += (fft.spectrum[i] - sum[i]) * smoothingFactor;


        }
        recalculate();
        drawCircles();
        timestamp++;

    }

    void recalculate() {
        if (timestamp % 5 == 0 || timestamp % 7 == 0) {
            buffer.addFirst(Triplet.of(
                    sum[1] * 700 + MIN_RADIUS, sum[10] * 3000 + MIN_RADIUS, sum[22] * 6000 + MIN_RADIUS));

            if (buffer.size() > 20) {
                buffer.removeLast();
            }

        }
    }

    void drawCircles() {
        rotateX(PI / 3);
        pushMatrix();
        buffer.forEach(x -> {
            translate(0, 0, -height / 20.0f);

            drawCircle(2 * width / 7, height / 3, x._1);
            drawCircle(width / 2, height / 3, x._2);
            drawCircle(5 * width / 7, height / 3, x._3);
        });
        popMatrix();

    }

    void drawCircle(int x, int y, float r) {
        strokeWeight(4);
        stroke(120, 255, 175, 10);
        circle(x, y, r + 6);
        circle(x, y, r - 6);
        circle(x, y, r + 8);
        circle(x, y, r - 8);
        stroke(70, 180, 120, 100);
        strokeWeight(3);
        circle(x, y, r);
    }

}
