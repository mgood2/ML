package com.meowster.omscs.ml.wekarun;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit tests for {@link CvMetrics}.
 */
public class CvMetricsTest {


    private static final List<Double> SET_A = new ArrayList<>(Arrays.asList(
            1.0, 2.0, 3.0, 4.0, 5.0
    ));
    private static final List<Double> SET_B = new ArrayList<>(Arrays.asList(
            5.0, 4.0, 3.0, 2.0, 1.0
    ));
    private static final List<Double> SET_C = new ArrayList<>(Arrays.asList(
            1.2, 3.0, 12.0, 0.6, 9.0
    ));

    private static final List<Double> AB_AVERAGE = new ArrayList<>(Arrays.asList(
            3.0, 3.0, 3.0, 3.0, 3.0
    ));
    private static final List<Double> ABC_AVERAGE = new ArrayList<>(Arrays.asList(
            2.4, 3.0, 6.0, 2.2, 5.0
    ));


    private static final double EPSILON = 1e-6;
    private static final String FMT_LIST_DIFF =
            "Lists differ: %s%n Expected : %s%n" +
                    " Actual   : %s%n";

    // comparing doubles for equality can be tricky...
    private void assertEqualsWithTolerance(String msg,
                                           List<Double> exp, List<Double> act) {
        int expLen = exp.size();
        int actLen = act.size();
        if (expLen != actLen) {
            fail(String.format(FMT_LIST_DIFF, msg, exp, act));
        }
        for (int i = 0; i < expLen; i++) {
            double e = exp.get(i);
            double a = act.get(i);
            if (e - a > EPSILON) {
                fail(String.format(FMT_LIST_DIFF, msg, exp, act));
            }
        }
    }


    private CvMetrics metrics;
    private List<Double> results;

    @Before
    public void before() {
        metrics = new CvMetrics();
    }

    @Test
    public void basic() {
        print("%s", metrics);
        assertEquals("not empty", 0, metrics.size());
        results = metrics.average();
        assertEquals("not an empty averages list", 0, results.size());
    }

    @Test
    public void singleSet() {
        metrics.add(SET_A);
        assertEquals("size not 1", 1, metrics.size());
        results = metrics.average();
        assertEqualsWithTolerance("average 1 wrong", SET_A, results);
    }

    @Test
    public void setsAB() {
        metrics.add(SET_A);
        metrics.add(SET_B);
        results = metrics.average();
        assertEqualsWithTolerance("average AB wrong", AB_AVERAGE, results);
    }

    @Test
    public void setsABC() {
        metrics.add(SET_A);
        metrics.add(SET_B);
        metrics.add(SET_C);
        results = metrics.average();
        assertEqualsWithTolerance("average ABC wrong", ABC_AVERAGE, results);
    }

}
