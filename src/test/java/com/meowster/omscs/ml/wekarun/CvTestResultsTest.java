package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.CvTestResults.Results;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Unit tests for {@link CvTestResults}.
 */
public class CvTestResultsTest {

    private static final List<Double> SET_A = new ArrayList<>(Arrays.asList(
            33.3, 66.6, 3.0, 4.0, 5.0
    ));
    private static final List<Double> SET_B = new ArrayList<>(Arrays.asList(
            95.0, 5.0, 3.0, 2.0, 1.0
    ));

    private CvTestResults results;

    @Test
    public void basic() {
        results = new CvTestResults(0, 2, null, null);
        print("%s", results.toString());
    }

    @Test
    public void something() {
        results = new CvTestResults(100, 5, null, null);
        results.saveResults(Results.CV, SET_A);
        results.saveResults(Results.TEST, SET_B);
        print("%s", results.toString());
    }
}
