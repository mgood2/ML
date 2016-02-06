package com.meowster.omscs.ml.wekarun;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import org.apache.commons.lang.time.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates Cross Validation and Test results.
 */
public class CvTestResults {

    private static final String EOL = String.format("%n");

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

        public String label() {
            return String.format("%5s: ", this.name());
        }
    }

    private final int numInstances;
    private final WekaClassifier wekaClassifier;
    private final FilterType filter;

    private final Map<Results, List<Double>> data = new HashMap<>();
    private final List<String> stopwatchInfo = new ArrayList<>();
    private final StopWatch stopWatch = new StopWatch();
    private String label = "(none)";

    /**
     * Creates a cross validation and test result holder.
     *
     * @param numInstances the total number of instances for this test
     * @param wekaClassifier the classifier used in this test
     * @param filter the filter used in this test
     */
    public CvTestResults(int numInstances, WekaClassifier wekaClassifier,
                         FilterType filter) {
        this.numInstances = numInstances;
        this.wekaClassifier = wekaClassifier;
        this.filter = filter;
    }

    /**
     * Starts the stopwatch, setting the label with the given tag and iteration.
     *
     * @param tag type of run
     * @param iteration iteration
     */
    public void startTiming(Results tag, int iteration) {
        label = String.format("%s-%d", tag, iteration);
        stopWatch.reset();
        stopWatch.start();
    }

    /**
     * Starts the stopwatch, setting the label with the given tag.
     *
     * @param tag type of run
     */
    public void startTiming(Results tag) {
        label = String.format("%s", tag);
        stopWatch.reset();
        stopWatch.start();
    }

    /**
     * Stops the stopwatch, recording the elapsed time for the current label.
     */
    public void stopTiming() {
        stopWatch.stop();
        stopwatchInfo.add(String.format("%-8s %s", label, stopWatch));
    }

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
            "       %Corr  %Inco  Preci  Recal  FMeas";
    //      "       99.99  99.99  99.99  99.99  99.99
    private static final String BLANK_ROW =
            "   -      -      -      -      -";

    private static final String FMT_DOUBLE = "%5.02f  ";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        formatStopwatchData(sb);

        sb.append(EOL)
                .append("Test Results: (number of instances = ")
                .append(numInstances).append(")").append(EOL).append(EOL)
                .append(COL_HEADERS).append(EOL);

        formatResults(sb, Results.CV);
        formatResults(sb, Results.TEST);
        return sb.toString();
    }

    private void formatResults(StringBuilder sb, Results type) {
        sb.append(type.label()).append(formatRow(data.get(type))).append(EOL);
    }

    private String formatRow(List<Double> doubles) {
        if (doubles == null) {
            return BLANK_ROW;
        }

        StringBuilder sb = new StringBuilder();
        for (double d: doubles) {
            sb.append(String.format(FMT_DOUBLE, d));
        }
        final int len = sb.length();
        sb.delete(len-2, len);
        return sb.toString();
    }


    private void formatStopwatchData(StringBuilder sb) {
        sb.append("Processing times:").append(EOL);
        for (String s: stopwatchInfo) {
            sb.append(" ").append(s).append(EOL);
        }
    }

    // TODO: provide data in form suitable for writing to CSV
}
