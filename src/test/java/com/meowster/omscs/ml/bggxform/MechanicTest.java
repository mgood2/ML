package com.meowster.omscs.ml.bggxform;


import org.junit.Test;

import static com.meowster.omscs.ml.bggxform.Mechanic.*;
import static com.meowster.omscs.ml.bggxform.Mechanic.fromString;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Mechanic}.
 */
public class MechanicTest {

    @Test
    public void basic() {
        assertEquals("not acting", ACTING, fromString("Acting"));
        assertEquals("not dice rolling", DICE_ROLLING, fromString("Dice Rolling"));
        assertEquals("not roll-spin", ROLL_SPIN_MOVE, fromString("Roll / Spin and Move"));
        assertEquals("not null", null, fromString("Something special"));
    }

}
