package com.meowster.omscs.ml.bggxform;


import org.junit.Test;

import static com.meowster.omscs.ml.bggxform.Subdomain.*;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link Subdomain}.
 */
public class SubdomainTest {

    @Test
    public void basic() {
        assertEquals("not abstract", ABSTRACT, fromString("Abstract Games"));
        assertEquals("not dice rolling", FAMILY, fromString("Family Games"));
        assertEquals("not null", null, fromString("What?"));
    }

}
