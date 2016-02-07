package com.meowster.omscs.ml.wekarun.config;

import com.meowster.omscs.ml.wekarun.classifier.WekaClassifier;
import weka.core.neighboursearch.LinearNNSearch;

/**
 * A configurable group of k-NN nearest neighbor (IBk) classifiers.
 */
public class VaryingIBk extends ClassifierGroup {

    private final int min;
    private final int max;
    private final int step;

    /**
     * Creates a varying k-NN classifier group for the given parameters.
     * <p/>
     * For example, one might write:
     * <pre>
     *     new VaryingIBk(3, 11, 2)
     * </pre>
     * to create classifiers for:
     * <pre>
     *     3-NN, 5-NN, 7-NN, 9-NN, 11-NN
     * </pre>
     *
     * @param min the minimum number of neighbors
     * @param max the maximum number of neighbors
     * @param step the increment step
     */
    public VaryingIBk(int min, int max, int step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    protected void configure() {
        for (int k = min; k <= max; k += step) {
            addParameterizedClassifier(k);
        }
    }

    private void addParameterizedClassifier(int k) {
        String tag = String.format("%d-NN", k);
        add(tag, WekaClassifier.Type.IBK,
                opt(_K, k),
                opt(_W, 0),
                opt(_A, classWithOptions(LinearNNSearch.class,
                        opt(_A, EUCLIDEAN_DISTANCE))
                )
        );
    }
}
