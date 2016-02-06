package com.meowster.omscs.ml.wekarun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates Cross Validation and Test results.
 */
public class CvTestResults {

    private static final String EOL = String.format("%n");

    private final int numInstances;

    /**
     * Creates a cross validation and test result holder.
     *
     * @param numInstances the total number of instances for this test
     */
    public CvTestResults(int numInstances) {
        this.numInstances = numInstances;
    }

    /**
     * Denotes the two types of results stored.
     */
    public enum Results {
        /**
         * Results from running the cross validation and averaging.
         */
        CV,

        /**
         * Results from evaluating the test data.
         */
        TEST;
    }

    private Map<Results, List<Double>> data = new HashMap<>();

    /**
     * Stores results from an evaluation.
     *
     * @param r      results type
     * @param values metrics from the evaluation
     */
    public void saveResults(Results r, List<Double> values) {
        data.put(r, values);
    }

    private static final String COL_HEADERS =
            "      %Corr  %Inco  Preci  Recal  FMeas";
    //      "      99.99  99.99  99.99  99.99  99.99
    private static final String BLANK_ROW =
            "  -      -      -      -      -";

    private static final String FMT_DOUBLE = "%5.02f  ";

    @Override
    public String toString() {

        return "CvTestResults: num instances = " + numInstances + EOL +
                COL_HEADERS + EOL +
                "  CV: " + formatResult(data.get(Results.CV)) + EOL +
                "TEST: " + formatResult(data.get(Results.TEST)) + EOL;
    }


    private String formatResult(List<Double> doubles) {
        if (doubles == null) {
            return BLANK_ROW;
        }

        StringBuilder sb = new StringBuilder();
        for (double d: doubles) {
            sb.append(String.format(FMT_DOUBLE, d));
        }
        final int len = sb.length();
        sb.replace(len-2, len, "");
        return sb.toString();
    }

    // TODO: provide data in form suitable for writing to CSV
}
