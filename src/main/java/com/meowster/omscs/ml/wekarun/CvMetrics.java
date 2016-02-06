package com.meowster.omscs.ml.wekarun;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Encapsulates Cross Validation metrics.
 */
public class CvMetrics {

    private final List<List<Double>> metrics = new ArrayList<>();


    /**
     * Adds a set of metrics to this collection.
     *
     * @param perfMetrics metrics to add
     */
    public void add(List<Double> perfMetrics) {
        metrics.add(perfMetrics);
    }

    /**
     * Computes an element-wise average of the list of arrays.
     *
     * @return a list of average values
     */
    public List<Double> average() {
        final int rows = metrics.size();
        if (rows == 0) {
            return Collections.emptyList();
        }
        final int cols = metrics.get(0).size();

        List<Double> results = new ArrayList<>(cols);
        for (int col = 0; col < cols; col++) {
            double sum = 0.0;
            for (List<Double> metric : metrics) {
                sum += metric.get(col);
            }
            results.add(sum / (double) rows);
        }
        return results;
    }

    /**
     * Returns the number of entries in the metrics list.
     *
     * @return number of metrics entries
     */
    public int size() {
        return metrics.size();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", metrics.size())
                .toString();
    }
}
