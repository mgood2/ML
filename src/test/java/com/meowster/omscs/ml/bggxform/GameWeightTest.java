package com.meowster.omscs.ml.bggxform;

import org.junit.Test;

import static com.meowster.omscs.ml.bggxform.GameWeight.*;
import static com.meowster.omscs.ml.bggxform.GameWeight.nearest;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link GameWeight}.
 */
public class GameWeightTest {

    @Test
    public void basic() {
        assertEquals("unexpected size", 5, GameWeight.values().length);
    }

    @Test
    public void nearestLight() {
        assertEquals("not 0.0", LIGHT, nearest(0.0));
        assertEquals("not 0.8", LIGHT, nearest(0.8));
        assertEquals("not 1.0", LIGHT, nearest(1.0));
        assertEquals("not 1.4", LIGHT, nearest(1.4));
        assertEquals("not 1.499", LIGHT, nearest(1.499));
    }

    @Test
    public void nearestMediumLight() {
        assertEquals("not 1.5", MEDIUM_LIGHT, nearest(1.5));
        assertEquals("not 1.7", MEDIUM_LIGHT, nearest(1.7));
        assertEquals("not 2.0", MEDIUM_LIGHT, nearest(2.0));
        assertEquals("not 2.4", MEDIUM_LIGHT, nearest(2.4));
        assertEquals("not 2.4999", MEDIUM_LIGHT, nearest(2.4999));
    }

    @Test
    public void nearestMedium() {
        assertEquals("not 2.5", MEDIUM, nearest(2.5));
        assertEquals("not 2.7", MEDIUM, nearest(2.7));
        assertEquals("not 3.0", MEDIUM, nearest(3.0));
        assertEquals("not 3.4", MEDIUM, nearest(3.4));
        assertEquals("not 3.4999", MEDIUM, nearest(3.4999));
    }

    @Test
    public void nearestMediumHeavy() {
        assertEquals("not 3.5", MEDIUM_HEAVY, nearest(3.5));
        assertEquals("not 3.7", MEDIUM_HEAVY, nearest(3.7));
        assertEquals("not 4.0", MEDIUM_HEAVY, nearest(4.0));
        assertEquals("not 4.4", MEDIUM_HEAVY, nearest(4.4));
        assertEquals("not 4.4999", MEDIUM_HEAVY, nearest(4.4999));
    }

    @Test
    public void nearestHeavy() {
        assertEquals("not 4.5", HEAVY, nearest(4.5));
        assertEquals("not 4.7", HEAVY, nearest(4.7));
        assertEquals("not 5.0", HEAVY, nearest(5.0));
        assertEquals("not 5.4", HEAVY, nearest(5.4));
        assertEquals("not 5.4999", HEAVY, nearest(5.4999));
        assertEquals("not 6.0", HEAVY, nearest(6.0));
    }

    @Test
    public void weightToString() {
        assertEquals("HEAVY", HEAVY.toString());
        assertEquals("LIGHT", LIGHT.toString());
    }

}
