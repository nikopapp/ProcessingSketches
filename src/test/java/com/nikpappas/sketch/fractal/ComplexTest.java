package com.nikpappas.sketch.fractal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexTest {

    @Test
    void multiply() {
        ComplexBD a = ComplexBD.of(-2, 1);
        ComplexBD b = ComplexBD.of(1, 3);
        assertEquals(a.multiply(b), b.multiply(a));
        assertEquals(ComplexBD.of(-2 - 3, -6 + 1), b.multiply(a));
    }

    @Test
    void multiply1() {
        ComplexBD a = ComplexBD.of(1, 1);
        ComplexBD b = ComplexBD.of(1, 1);
        assertEquals(a.multiply(b), b.multiply(a));
        assertEquals(ComplexBD.of(1 - 1, 1 + 1), b.multiply(a));
    }

    @Test
    public void compareGrater() {
        ComplexBD a = ComplexBD.ZERO;
        ComplexBD b = ComplexBD.of(1, -1);
        ComplexBD c = ComplexBD.of(1000000000, -1);
        ComplexBD d = ComplexBD.of(-1000000000, -2);
        assertTrue(b.compareTo(a) > 0);
        assertTrue(c.compareTo(a) > 0);
        assertTrue(c.compareTo(b) > 0);
        assertTrue(d.compareTo(c) > 0);
    }
    @Test
    public void compareEquals() {
        ComplexBD a = ComplexBD.ZERO;
        ComplexBD b = ComplexBD.of(0,1);
        ComplexBD c = ComplexBD.of(1,0);
        assertTrue(a.compareTo(a) == 0);
        assertTrue(b.compareTo(b) == 0);
        assertTrue(c.compareTo(c) == 0);
        assertTrue(b.compareTo(c) == 0);
        assertTrue(c.compareTo(b) == 0);
    }

}